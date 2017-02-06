package br.com.pilovieira.tk303g.persist;

import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DaoManager {

    public static <T> List<T> getAll(Context context, Class<T> clazz) {
        return apply(context, clazz, new Operation() {
            @Override
            public List apply(DaoBuilder dao) throws SQLException {
                return dao.queryForAll();
            }
        });
    }

    public static <T> List<T> search(Context context, Class<T> clazz, final RWhere restrictions) {
        return apply(context, clazz, new Operation() {
            @Override
            public List apply(DaoBuilder dao) throws SQLException {
                Where where = dao.queryBuilder().where();
                return restrictions.where(where);
            }
        });
    }

    public static <T> List<T> search(Context context, Class<T> clazz, final String attribute, final Object value) {
        if (value == null)
            return new ArrayList<>();

        return apply(context, clazz, new Operation() {
            @Override
            public List apply(DaoBuilder dao) throws SQLException {
                return dao.queryForEq(attribute, value);
            }
        });
    }

    public static int min(Context context, Class<?> clazz, String attribute, RWhere where) {
        String min = firstElement(context, clazz, where, String.format("MIN(%s)", attribute));
        return min == null ? -1 : Integer.parseInt(min);
    }

    public static int max(Context context, Class<?> clazz, String attribute, RWhere where) {
        String min = firstElement(context, clazz, where, String.format("MAX(%s)", attribute));
        return min == null ? -1 : Integer.parseInt(min);
    }

    public static int sum(Context context, Class<?> clazz, String attribute, RWhere where) {
        String min = firstElement(context, clazz, where, String.format("SUM(%s)", attribute));
        return min == null ? 0 : Integer.parseInt(min);
    }

    public static int count(Context context, Class clazz, RWhere where) {
        String min = firstElement(context, clazz, where, "COUNT(*)");
        return min == null ? 0 : Integer.parseInt(min);
    }

    public static String firstElement(Context context, Class clazz, RWhere where, String... functions) {
        List<String[]> list = functions(context, clazz, where, functions);
        return list.isEmpty() ? null : list.get(0)[0];
    }

    public static void truncate(Context context, Class clazz) throws SQLException {
        DaoBuilder dao = getDaoBuilder(context, clazz);
        try {
            TableUtils.clearTable(dao.getConnectionSource(), clazz);
        } finally {
            dao.close();
        }
    }

    private static List<String[]> functions(Context context, Class clazz, final RWhere where, final String... functions) {
        return apply(context, clazz, new Operation() {
            @Override
            public List apply(DaoBuilder dao) throws SQLException {
                QueryBuilder builder = dao.queryBuilder();
                if (where != null)
                    where.where(builder.where());
                builder.selectRaw(functions);
                return dao.queryRaw(builder.prepareStatementString()).getResults();
            }
        });
    }

    public static void persist(Context context, final Object object) {
        apply(context, object.getClass(), new Operation() {
            @Override
            public List apply(DaoBuilder dao) throws SQLException {
                dao.createOrUpdate(object);
                return null;
            }
        });
    }

    public static void delete(Context context, final List objects) {
        if(objects.isEmpty())
            return;

        apply(context, objects.get(0).getClass(), new Operation() {
            @Override
            public List apply(DaoBuilder dao) throws SQLException {
                dao.delete(objects);
                return null;
            }
        });
    }

    public static List apply(Context context, Class clazz, Operation operation) {
        DaoBuilder dao = getDaoBuilder(context, clazz);
        try {
            return operation.apply(dao);
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return new ArrayList();
    }

    private static DaoBuilder getDaoBuilder(Context context, Class clazz) {
        return DaoBuilder.getInstance(context, clazz);
    }

/*    public static class PersistCallback<T> implements DialogClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which, Serializable object) {
            Context context = ((Dialog) dialog).getContext();
            persist(context, object);
        }
    }

    public static class DeleteCallback<T> implements DialogClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which, Serializable object) {
            Context context = ((Dialog) dialog).getContext();
            delete(context, Arrays.asList(object));
        }
    }*/

    public interface RWhere {
        List where(Where w) throws SQLException;
    }

    private interface Operation {
        List apply(DaoBuilder dao) throws SQLException;
    }

    private DaoManager(){}
}

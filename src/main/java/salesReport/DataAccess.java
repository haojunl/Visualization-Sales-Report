package salesReport;

import org.jfree.data.category.DefaultCategoryDataset;
import java.sql.*;

public class DataAccess {

    //createStatement 预编译SQL（sql语句的定义）,用于执行static SQL于并返回其生成的结果对象
    //prepareStatement 用于指向dynamic SQL
    public static Statement makeStatement(Connection conn) throws SQLException {
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement: " + e.getMessage());
            throw e;
        }
    }

    //execute SQL query, retrieve result as ResultSet object(结果集合)
    public static ResultSet executeQuery(Statement statement, String Query) throws SQLException{
        if(Query == null || Query.isEmpty()){
            throw new IllegalArgumentException("Query cannot be null or empty!");
        }

        try{
            return statement.executeQuery(Query);
        }catch(SQLException e){
            System.out.println("Failed to execute query: " + e.getMessage());
            throw e;
        }
    }

    //get data from resultSet, retrieve result as dataSet
    public static DefaultCategoryDataset get_dataset(DefaultCategoryDataset dataset, ResultSet resultSet) {
        if(resultSet==null) {
            throw new IllegalArgumentException("ResultSet cannot be null");
        }

        try{
            while(resultSet.next()){//true: goto next row, false:
                String albumTitle = resultSet.getString("AlbumTitle");//get value using col label
                int sales = resultSet.getInt("Sales");
                dataset.setValue(sales, "Sales", albumTitle);//saved to dataset
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        assert dataset != null;
        return dataset;
    }


    public static DefaultCategoryDataset readChinookDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        /* Create SQLite Database Connection */
        try {
            Connection conn = Connect.connect();//connect to DB
            Statement statement = makeStatement(conn);// process statement

            String Query = "SELECT albums.Title AS AlbumTitle, SUM(invoice_items.Quantity) AS Sales " +
                    "FROM albums " +
                    "INNER JOIN tracks ON albums.AlbumId = tracks.AlbumId " +
                    "INNER JOIN invoice_items ON tracks.TrackId = invoice_items.TrackId " +
                    "GROUP BY albums.AlbumId " +
                    "ORDER BY Sales DESC " +
                    "LIMIT 5";
            ResultSet resultSet = executeQuery(statement, Query);
            //get dataset from result(Query)
            dataset = get_dataset(dataset,resultSet);
            //close Connection,statement.ResultSet
            statement.close();
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataset;
    }
}

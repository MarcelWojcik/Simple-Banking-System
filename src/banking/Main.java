package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;


public class Main {

    @Parameter(names = {"-fileName"}, description = "Database file name")
    String fileName;

    static SQLiteDataSource dataSource = new SQLiteDataSource();



    public static void main(String[] args){

        init(args);
        Menu.start();

    }

    public static void init(String[] args) {

        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);


        String url = "jdbc:sqlite:" + main.fileName;
        dataSource.setUrl(url);

        try(Connection con = dataSource.getConnection()){
            try(Statement statement = con.createStatement()){
                statement.executeUpdate(
                        """
                                CREATE TABLE IF NOT EXISTS card (
                                id INTEGER PRIMARY KEY AUTOINCREMENT,
                                number VARCHAR(16) UNIQUE,
                                pin VARCHAR(4) NOT NULL,
                                balance INTEGER DEFAULT 0
                                );
                            """
                );
            }catch(SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
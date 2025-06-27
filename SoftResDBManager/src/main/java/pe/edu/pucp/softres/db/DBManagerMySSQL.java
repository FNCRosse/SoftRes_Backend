package pe.edu.pucp.softres.db;

/**
 *
 * @author frank
 */
public class DBManagerMySSQL extends DBManager{

    @Override
    protected String getURL() {
        String url = this.tipo_de_driver.concat("://");
        url = url.concat(this.nombre_de_host);
        url = url.concat(":");
        url = url.concat(this.puerto);
        url = url.concat(";");
        url = url.concat("databaseName=" + this.base_de_datos);
        url = url.concat(";encrypt=false");
        //System.out.println(url);
        return url;
    }

    @Override
    public String retornarSQLParaUltimoAutoGenerado() {
        String sql = "select @@IDENTITY as id";
        return sql;
    }
    
}

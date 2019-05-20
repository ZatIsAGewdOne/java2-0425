package lt.bit.java2.listeners

import lt.bit.java2.db.DBUtils
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

@WebListener
class AppListener : ServletContextListener {

    override fun contextInitialized(sce: ServletContextEvent?) {
        println("contextInitialized");
        DBUtils.getEntityManager();
    }
}

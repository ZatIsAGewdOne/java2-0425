package lt.bit.java2.listeners

import lt.bit.java2.db.DBUtils
import org.slf4j.LoggerFactory
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

@WebListener
class AppListener : ServletContextListener {

    private val logger = LoggerFactory.getLogger(AppListener::class.java)

    override fun contextInitialized(sce: ServletContextEvent?) {

        logger.info("contextInitialized ... start")

        DBUtils.getEntityManager()

        logger.info("contextInitialized ... done")
    }
}

package dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestUserDAO.class, TestNewsDAO.class,TestCommentDAO.class })
public class AllTests {

}

package com.javaclasses.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({FileServiceImplShould.class, FileSharingServiceConcurrencyTest.class,
        UserAuthenticationServiceShould.class, UserRegistrationServiceShould.class})
public class AllTests {

}

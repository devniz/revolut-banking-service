package com.revolut.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.di.AppModule;
import spark.servlet.SparkApplication;
import spark.servlet.SparkFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public class SparkGuiceFilter extends SparkFilter {

    private Injector injector = null;

    @Override
    protected SparkApplication[] getApplications(final FilterConfig filterConfig) throws ServletException {
        final SparkApplication[] applications = super.getApplications(filterConfig);
        if (this.injector == null) {
            this.injector = Guice.createInjector(new AppModule());
        }
        if (applications != null && applications.length != 0) {
            for (SparkApplication application : applications) {
                this.injector.injectMembers(application);
            }
        }

        return applications;
    }
}
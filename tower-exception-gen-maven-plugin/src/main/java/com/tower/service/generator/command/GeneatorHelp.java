package com.tower.service.generator.command;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Created by kevin on 14/12/31.
 * @goal help
 */
public class GeneatorHelp  extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("run command : mvn exception-generator:gen");
    }
}

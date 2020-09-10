package com.cpqd.wf.rest.util;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import com.cpqd.wf.rest.WebServer;

public class MavenUtil {

	public static Model readPom() throws Exception {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model;
        if ((new File("pom.xml")).exists()) {
            model = reader.read(new FileReader("pom.xml"));
        } else {
            model = reader.read(new InputStreamReader(WebServer.class.getResourceAsStream("/META-INF/maven/com.cpqd/nwf-rest-service-web/pom.xml")));
        }
        return model;
	}
	
}

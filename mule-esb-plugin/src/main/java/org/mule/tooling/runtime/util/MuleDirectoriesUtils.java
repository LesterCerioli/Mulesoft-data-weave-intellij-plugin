package org.mule.tooling.runtime.util;

import com.intellij.openapi.module.Module;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MuleDirectoriesUtils {

    public static final String SRC_MAIN_MULE = "src/main/mule";
    public static final String SRC_MAIN_RESOURCES = "src/main/resources";
    public static final String SRC_MAIN_JAVA = "src/main/java";
    public static final String SRC_MAIN_API = "src/main/resources/api";

    public static final String SRC_TEST_MUNIT = "src/test/munit";
    public static final String SRC_TEST_RESOURCES = "src/test/resources";

    public static final String SRC_TEST_DWIT = "src/test/dwit";

    public static File getMavenTooling() {
        File maven_repository = new File(new File(getHomeDirectory(), ".m2"), "repository");
        if (!maven_repository.exists()) {
            maven_repository.mkdirs();
        }
        return maven_repository;
    }

    public static File getRuntimeSchemaDirectory(String muleVersion) {
        File schemas = new File(getMuleHomeDirectory(muleVersion), "schemas");
        if (!schemas.exists()) {
            schemas.mkdirs();
        }
        return schemas;
    }

    public static File getMuleHomeDirectory(String muleVersion) {
        File globalMuleIde = new File(getHomeDirectory(), ".mule_ide");
        if (!globalMuleIde.exists()) {
            globalMuleIde.mkdirs();
        }
        return new File(globalMuleIde, muleVersion);
    }

    public static File getMuleRuntimesHomeDirectory() {
        File runtimes = new File(getHomeDirectory(), ".mule_runtimes");
        if (!runtimes.exists()) {
            runtimes.mkdirs();
        }
        return runtimes;
    }

    public static File getMuleWorkingDirectory(Module module) {
        File moduleFile = new File(module.getModuleFilePath());
        if (moduleFile.exists()) {
            File moduleHome = moduleFile.getParentFile();
            File app = new File(new File(moduleHome, ".mule_ide"), "app");
            if (!app.exists()) {
                app.mkdirs();
            }
            return app;
        } else {
            File projectBasePath = new File(module.getProject().getBasePath());
            File app = new File(new File(projectBasePath, ".mule_ide"), "app");
            if (!app.exists()) {
                app.mkdirs();
            }
            return app;
        }
    }

    public static File getSettingsXml() {
        return new File(new File(getHomeDirectory(), ".m2"), "settings.xml");
    }

    public static File getHomeDirectory() {
        final String currentUsersHomeDir = System.getProperty("user.home");
        return new File(currentUsersHomeDir);
    }

    public static String getRelativePath(String absolutePath, String appPath) {
        Path pathAbsolute = Paths.get(absolutePath);
        Path pathBase = Paths.get(appPath);
        Path pathRelative = pathBase.relativize(pathAbsolute);

        return pathRelative.toString();
    }
}

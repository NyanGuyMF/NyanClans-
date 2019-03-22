/*
 * This file is part of NyanClans Bukkit plug-in.
 *
 * NyanClans is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NyanClans is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NyanClans. If not, see <https://www.gnu.org/licenses/>.
 */
package nyanclans.utils.dependency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.plugin.java.JavaPlugin;

/** @author NyanGuyMF - Vasiliy Bely */
public final class DependencyManager {
    private ReflectionClassLoader reflectionClassLoader;
    private File dependenciesFolder;

    public enum DriverDownloadStatus {
        HASH_ERROR, CONNECTION_ERROR,
        DOWNLOAD_ERROR, SUCCESS
    }

    public DependencyManager(final File pluginFolder, final JavaPlugin plugin) {
        dependenciesFolder    = new File(pluginFolder, "dependencies");
        reflectionClassLoader = new ReflectionClassLoader(plugin);

        if (!dependenciesFolder.exists()) {
            dependenciesFolder.mkdir();
        }
    }

    /**
     * Downloads given driver.
     * <p>
     * Returns {@link DriverDownloadStatus#HASH_ERROR} if
     * MD5 hash code of downloaded file doesn't equals
     * equals expected hash from {@link DatabaseDriver}.
     * <p>
     * Returns {@link DriverDownloadStatus#DOWNLOAD_ERROR}
     * if got {@link IOException} while downloading file.
     * <p>
     * Returns {@link DriverDownloadStatus#CONNECTION_ERROR}
     * if got {@link IOException} while opening connection
     * with {@link URL} from {@link DatabaseDriver}.
     *
     * @param   driver  Driver instance to download.
     * @return {@link DriverDownloadStatus#SUCCESS} if
     * download was successful.
     */
    public DriverDownloadStatus downloadDriver(final DatabaseDriver driver) {
        URLConnection conn = null;

        try {
            conn = driver.getDownloadUrl().openConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
            return DriverDownloadStatus.CONNECTION_ERROR;
        }

        try (InputStream in = conn.getInputStream()) {
            File dependencyFile = new File(dependenciesFolder, driver.getFileName());

            Files.copy(in, dependencyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            if (!checkHashSum(dependencyFile, driver)) {
                dependencyFile.delete();
                return DriverDownloadStatus.HASH_ERROR;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return DriverDownloadStatus.DOWNLOAD_ERROR;
        }

        return DriverDownloadStatus.SUCCESS;
    }

    /**
     * Loads given driver into java runtime.
     * <p>
     * Returns <tt>false</tt> if driver's file has bad md5 hash sum.
     * <p>
     * It will return <tt>false</tt> if driver's file doesn't exists.
     *
     * @param   driver  Driver instance you want to download.
     * @return <tt>true</tt> if driver loaded successfully.
     */
    public boolean loadDriver(final DatabaseDriver driver) {
        final File driverFile = new File(dependenciesFolder, driver.getFileName());

        if (!checkHashSum(driverFile, driver))
            return false;

        return loadCustomDriver(driver.getDriverName());
    }

    /**
     * Gets {@link DatabaseDriver} instance for given driver name.
     * <p>
     * If such {@link DatabaseDriver} doesn't exists it will
     * return <tt>null</tt> value.
     *
     * @param   driverName  Driver's name you want to get without «-driver.jar» suffix.
     * @return {@link DatabaseDriver} instance for given name or <tt>null</tt>.
     */
    public DatabaseDriver getSecureDriver(final String driverName) {
        DatabaseDriver secureDriver = null;

        for (DatabaseDriver driver : DatabaseDriver.values()) {
            if (driver.getDriverName().equalsIgnoreCase(driverName)) {
                secureDriver = driver;
            }
        }

        return secureDriver;
    }

    /**
     * Loads given driver into java runtime.
     * <p>
     * This method doesn't fully secure, may load bad jar.
     * <p>
     * It will return <tt>false</tt> if driver's file doesn't exists.
     *
     * @param   driverName  Driver's name you want to load form
     * dependencies plug-in folder without «-driver.jar» suffix.
     * @return <tt>true</tt> if driver loaded successfully.
     */
    public boolean loadCustomDriver(final String driverName) {
        return loadJar(new File(dependenciesFolder, driverName + "-driver.jar"));
    }

    private boolean loadJar(final File jar) {
        // We cannot load driver if it doesn't exists
        if (!jar.exists())
            return false;

        return reflectionClassLoader.loadJar(jar.toPath());
    }

    /**
     * Check is given driver file exists.
     *
     * @param   driveName  Driver's name without suffix «-driver.jar».
     * @return <tt>true</tt> if driver exists.
     */
    public boolean isDriverFileExists(final String driveName) {
        return new File(dependenciesFolder, driveName + "-driver.jar").exists();
    }

    /**
     * Compares md5 hash sum of given file with given driver.
     *
     * @param   driverFile  Driver's file to get hash sum.
     * @param   driver      Driver class by itself.
     * @return <tt>true</tt> if hash sum are equal.
     */
    public boolean checkHashSum(final File driverFile, final DatabaseDriver driver) {
        String fileHash;

        try (InputStream in = new FileInputStream(driverFile)) {
            fileHash = DigestUtils.md5Hex(in);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return fileHash.equals(driver.getMd5sum());
    }
}

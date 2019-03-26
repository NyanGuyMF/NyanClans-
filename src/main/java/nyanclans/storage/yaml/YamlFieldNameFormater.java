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
package nyanclans.storage.yaml;

import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration.BukkitYamlProperties;
import de.exlll.configlib.format.FieldNameFormatter;

/**
 * Singleton pattern.
 *
 * @author NyanGuyMF
 */
public final class YamlFieldNameFormater implements FieldNameFormatter {
    private static BukkitYamlProperties props;

    private YamlFieldNameFormater() {}

    /**
     * Sets "-" between lower and upper case chars and
     * replaces upper char with similar lower case.
     * <p>
     * Example: "myAweomeFieldName" will be "my-awesome-field-name".
     *
     * @param   fieldName   Class field to format.
     * @return Formated field name.
     */
    @Override
    public String fromFieldName(final String fieldName) {
        StringBuilder builder = new StringBuilder(fieldName.length());

        for (char c : fieldName.toCharArray()) {
            if (Character.isLowerCase(c)) {
                builder.append(c);
            } else if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
                builder.append('-').append(c);
            }
        }

        return builder.toString();
    }

    public static FieldNameFormatter getInstance() {
        if (YamlFieldNameFormater.props != null)
            return YamlFieldNameFormater.props.getFormatter();

        return getProps().getFormatter();
    }

    public static BukkitYamlProperties getProps() {
        if (YamlFieldNameFormater.props != null)
            return YamlFieldNameFormater.props;

        YamlFieldNameFormater.props = BukkitYamlProperties.builder()
                .addFilter(fn -> !fn.getName().startsWith("ignore"))
                .setFormatter(new YamlFieldNameFormater())
                .build();

        return YamlFieldNameFormater.props;
    }
}

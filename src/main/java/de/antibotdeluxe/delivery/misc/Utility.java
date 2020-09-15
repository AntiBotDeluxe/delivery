package de.antibotdeluxe.delivery.misc;

import de.antibotdeluxe.delivery.misc.exceptions.PortAlreadyBoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.stream.Collectors;

/**
 * Class will hold different <i>miscellaneous</i> methods and constants that are <i>crucial</i> for running this application in any
 * environment.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class Utility {

    /**
     * Default symbol for separating and joining mainly {@link String}'s apart and together.
     */
    public static final String DEFAULT_DELIMITER = "::";

    /**
     * Private constructor against code smell.
     */
    Utility() { }

    /**
     * Checks if the given port is available and suitable for running the application.
     * <p>
     * A suitable one has to be:<br>
     * <ul>
     *  <li><code>at least 4 digits long</code></li>
     *  <li><code>at most 5 digits long</code></li>
     *  <li><code>above +1.023 and below +65.535</code></li>
     * </ul>
     * <p>
     * The port has to match all the criteria otherwise an {@link IllegalArgumentException} will be thrown.<br>
     * If the port is already bound a {@link PortAlreadyBoundException} will be thrown.
     *
     * @param port
     *          The port which should be checked
     * @return Boolean including the expression whether the port is bound or not
     */
    public static boolean checkPortAvailability(int port) {
        if (!(port > 1023 && port < 65536))
            throw new IllegalArgumentException("The port regulations were violated. This port is not suitable. {port: " + port + "}");
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
        } catch (IOException exception) {
            throw new PortAlreadyBoundException(port);
        }
        return true;
    }

    /**
     * Transforms any given {@link List} into a single {@link String}.<br>
     * This will be a benefit if a large amount of data need to be sent.
     *
     * @param list
     *          The {@link List} which should be converted into a {@link String} including all of its content.
     * @param delimiter
     *          A {@link CharSequence} or {@link String} which is used to join the single elements together and separate them later on
     * @return A compressed version of any {@link List} as {@link String} with the given delimiter
     */
    public static String convertListToString(List<?> list, String delimiter) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }

    /**
     * Transforms any given {@link List} into a single {@link String} by joining the the single elements together with the given
     * delimiter.<br>
     * This method has only one difference with the <code>convertListToString(List list, String delimiter)</code> method.<br>
     * <p>
     * <strong>Notice</strong><br>
     * The delimiter is already set to the <i>DEFAULT_DELIMITER</i>.
     *
     * @param list
     *          The {@link List} which should be converted into a {@link String} including all of its content.
     * @return A compressed version of any {@link List} as {@link String} with the defined delimiter <strong>", "</strong>
     */
    public static String convertListToString(List<?> list) {
        return convertListToString(list, DEFAULT_DELIMITER);
    }

    /**
     * Transforms any {@link String} into a {@link List} by separating the {@link String} with the given delimiter.
     *
     * @param compressed
     *          Compressed {@link List} as {@link String } from <code>convertListToString(List list)</code>
     * @param delimiter
     *          Symbol with which the {@link List} was joined together
     * @return {@link List} containing all the elements from the original {@link List}
     */
    public static List<String> convertStringToList(String compressed, String delimiter) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, compressed.split(delimiter));
        return list;
    }

    /**
     * Transforms any {@link String} into a {@link List}.
     * <p>
     * <strong>Notice</strong><br>
     * The default delimiter symbol will be used by using this method.
     *
     * @param compressed
     *          Compressed {@link List} from <code>convertListToString(List list)</code>
     * @return {@link List} containing all the elements from the original {@link List}
     */
    public static List<String> convertStringToList(String compressed) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, compressed.split(DEFAULT_DELIMITER));
        return list;
    }

}

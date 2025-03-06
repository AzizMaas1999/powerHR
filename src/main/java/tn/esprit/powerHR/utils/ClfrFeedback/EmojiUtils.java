package tn.esprit.powerHR.utils.ClfrFeedback;
import com.vdurmont.emoji.EmojiParser;

public class EmojiUtils {
    // Fonction qui remplace les émoticônes texte par des emojis Unicode
    public static String replaceEmoticons(String text) {
        text = text.replace(";)", ":wink:")
                .replace("<3", ":heart:")
                .replace(":D", ":grinning:");

        return EmojiParser.parseToUnicode(text);
    }

    public static void main(String[] args) {
        String message = "Merci pour ton aide ;) Ce projet est génial <3 :D";
        String convertedMessage = replaceEmoticons(message);
        System.out.println(convertedMessage); // Affiche le texte avec des emojis
    }
}

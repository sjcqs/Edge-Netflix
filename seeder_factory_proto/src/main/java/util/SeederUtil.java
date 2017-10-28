package util;

import route.Seeder;
import route.Video;

/**
 * Created by satyan on 10/28/17.
 *
 */
public final class SeederUtil {
    public static String printSeeder(Seeder seeder){
        String str = "";
        Video video = seeder.getVideo();
        str += video.getName();
        if (video.getKeywordCount() != 0){
            str += " (";
            for (String keyword : video.getKeywordList()){
                str += keyword + ";";
            }
            str += ")";
        }
        return str;
    }
}

package model.tempRealization;

import model.tempController.QueryFromYoutube;
import model.youTubeDataContainer.GeneralDataContainer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Main {
    private static String channelId = "UC8mndg3Tr2g9iMoJMI4ob5A";
    private static String channelId2 = "UCXFxgPppcehs2LoiKhkakQg";
    private static String channelId3 = "UCUoEWMvijAEn-hWTLUtfdoA";
    public static void main(String[] args) throws InterruptedException {
//        test1();
//        Thread.sleep(700);
//        System.out.println("\n=================================================\n");
//        test2();
//        test3(channelId, channelId2, channelId3);
//        test4("UCcTsXM2XgBAZhS1VpnCd2_A");//out playlists
        test4(channelId, channelId2);//out playlists (uploads)
    }

    /**
     *
     */
    static void test4(String ...channelsId){
        int length = channelsId.length;
        QueryFromYoutube youtubeQuery = new QueryFromYoutube();
        GeneralDataContainer[] containers = youtubeQuery.getAllDataContainers(channelsId);
        for (int i = 0; i < length; i++) {
            System.out.println(containers[i].getUploads());
        }
    }

    /**
     * тест №1 - отримуєм назву каналу по айді
     * @throws InterruptedException - лінь ловити в try/catch
     */
    private static void test1() throws InterruptedException{
        System.out.printf("Test 1: \"Query one channel\". \n" +
                "Channel id: %s\n" +
                "Task: get title name from YouTubeAPI, by channel id\n\n", channelId);
        Thread.sleep(700);

        QueryFromYoutube query = new QueryFromYoutube();
        query.setChannelId(channelId);
        query.makeQuary();
        String tittle = query.getTitle();
        System.out.println("Tittle = \"" + tittle + "\"");
    }

    /**
     * тест №2 - отримуємо всі необхідні данні
     *  назва каналу - tittle
     *  кількість переглядів - viewCount
     *  кількість відео -videoCount
     *  subscribeHidden - булєвське значення - чи приховано в налаштуваннях інфа по фоловерах
     *  subscribeCount - кількість фоловерів
     *  published - час створення каналу
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException{
        System.out.printf("Test 2: \"Query one channel\". \n" +
                "Channel id: %s\n" +
                "Task: get all fields from YouTubeAPI, by channel id\n\n", channelId);


        QueryFromYoutube query = new QueryFromYoutube();
        query.setChannelId(channelId);
//(!) **тут затримка в запиті - знайти і помістити в окремий потік
        query.makeQuary();

        String tittle = query.getTitle();
        Integer viewCount = query.getViewCount();
        Integer videoCount = query.getVideoCount();
        Integer subscriberCount = query.getSubscriberCount();
        Boolean subscriberHidden = query.getHiddenSubscriberCount();
        Instant published = query.getPublishedAt();


        System.out.println("Tittle            : \"" + tittle + "\"");
        Thread.sleep(900);
        System.out.println("View count        = " + viewCount);
        Thread.sleep(900);
        System.out.println("Video count       = " + videoCount);
        Thread.sleep(900);
        System.out.println("Subscriber hidden = " + subscriberHidden);
        Thread.sleep(900);
        System.out.println("Subscriber count  = " + subscriberCount);
        Thread.sleep(900);
        System.out.println("Published at      : " + new Main().publishedToString(published));
    }

    /**
     * Тест №3:
     * Отримання массиву по декільком айді
     */
    private static void test3(String ...channelsId){
        QueryFromYoutube youtubeQuery = new QueryFromYoutube();
        GeneralDataContainer[] dataContainers = youtubeQuery.getAllDataContainers(channelsId);
        int count = 0;
        for (GeneralDataContainer gdc:
             dataContainers) {
            try {
                System.out.println("Channel #" + ++count);
                sysoutAll(gdc);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String publishedToString(Instant timestamp){
        LocalDateTime ldt = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        return String.format("%s %d %d at %d:%d%n", ldt.getMonth(), ldt.getDayOfMonth(),
                ldt.getYear(), ldt.getHour(), ldt.getMinute());
    }

    private static void sysoutAll(GeneralDataContainer generalDataContainer) throws InterruptedException{
        int delay = 700;

        String tittle = generalDataContainer.getTitle();
        Integer viewCount = generalDataContainer.getViewCount();
        Integer videoCount = generalDataContainer.getVideoCount();
        Integer subscriberCount = generalDataContainer.getSubscriberCount();
        Boolean subscriberHidden = generalDataContainer.getHiddenSubscriberCount();
        Integer commentCount = generalDataContainer.getCommentCount();
        Instant published = generalDataContainer.getPublishedAt();
        String id = generalDataContainer.getId();


        System.out.println("Tittle            : \"" + tittle + "\"");
        Thread.sleep(delay);
        System.out.println("ID                : \"" + id + "\"");
        Thread.sleep(delay);
        System.out.println("View count        = " + viewCount);
        Thread.sleep(delay);
        System.out.println("Video count       = " + videoCount);
        Thread.sleep(delay);
        System.out.println("Subscriber hidden = " + subscriberHidden);
        Thread.sleep(delay);
        System.out.println("Subscriber count  = " + subscriberCount);
        Thread.sleep(delay);
        System.out.println("Coment count      = " + commentCount);
        Thread.sleep(delay);
        System.out.println("Published at      : " + new Main().publishedToString(published));
        Thread.sleep(delay);
    }
}

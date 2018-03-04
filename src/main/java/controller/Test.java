package controller;


public class Test {
    private static String channelId = "UC8mndg3Tr2g9iMoJMI4ob5A";

    public static void main(String[] args) {

        MainController mc = new MainController();
        mc.makeRequest(channelId);
        System.out.println("Channel name: " + mc.getChannelName());
        System.out.println("Channel views count: " + mc.getViewCount());
        System.out.println("Channel videos count: " + mc.getVideoCount());
        System.out.println("Channel subscribers count: " + mc.getSubscriberCount());
        System.out.println("Channel date: " + mc.getPublishingDate());
    }

}

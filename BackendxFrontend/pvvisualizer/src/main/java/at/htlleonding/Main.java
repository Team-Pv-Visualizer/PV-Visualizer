package at.htlleonding;

import lombok.SneakyThrows;

import static at.htlleonding.httpclient.Client.getData;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        getData();
    }
}
package com.example.game_caro_client.hubs;

public class Message {
    public int id;
    public String data = "";

    public Message(int id) {
        this.id = id;
        data = "";
    }

    public Message(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void write(String value) {
        if (data == null || data.equals("")) {
            data = value;
            return;
        }
        data += "|" + value;
    }

    public String ReadString()
    {
        if (data.startsWith("|"))
        {
            data = data.substring(1);
        }

        if (!data.contains("|"))
        {
            return data;
        }

        String result = data.split("\\|")[0];
        data = data.substring(result.length());

        return result;
    }

    public Long ReadLong()
    {
        if (data.startsWith("|"))
        {
            data = data.substring(1);
        }

        if (!data.contains("|"))
        {
            return Long.parseLong(data);
        }

        String result = data.split("\\|")[0];
        data = data.substring(result.length());

        return Long.parseLong(result);
    }

    public int ReadInt()
    {
        if (data.startsWith("|"))
        {
            data = data.substring(1);
        }

        if (!data.contains("|"))
        {
            return Integer.parseInt(data);
        }

        String result = data.split("\\|")[0];
        data = data.substring(result.length());

        return Integer.parseInt(result);
    }
}

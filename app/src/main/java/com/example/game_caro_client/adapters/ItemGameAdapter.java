package com.example.game_caro_client.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.game_caro_client.R;
import com.example.game_caro_client.models.ItemGame;
import com.example.game_caro_client.models.Room;
import com.example.game_caro_client.screens.GameScr;

import java.util.ArrayList;
import java.util.List;

public class ItemGameAdapter extends ArrayAdapter<ItemGame> {
    public Context context;
    public ArrayList<ItemGame> itemGames;

    public ItemGameAdapter(@NonNull Context context, int resource, @NonNull List<ItemGame> objects) {
        super(context, resource, objects);
        this.context = context;
        this.itemGames = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_game, null);
        }

        if (itemGames.size() > 0) {
            ItemGame itemGame = itemGames.get(position);
            ImageView imageCustom = convertView.findViewById(R.id.img_item_game);

            if (itemGame.data == 0) {
                imageCustom.setImageResource(R.drawable.img_bgr_item_game);
            }

            if (itemGame.data == 1) {
                if (Room.lastIndexSelected == position) {
                    imageCustom.setImageResource(R.drawable.img_o_select);
                }
                else {
                    imageCustom.setImageResource(R.drawable.img_o);
                }
            }

            if (itemGame.data == 2) {
                if (Room.lastIndexSelected == position) {
                    imageCustom.setImageResource(R.drawable.img_x_select);
                }
                else {
                    imageCustom.setImageResource(R.drawable.img_x);
                }
            }
        }

        return convertView;
    }

    public void update(ArrayList<ItemGame> arr) {
        for (int i =0; i < itemGames.size(); i++) {
            itemGames.get(i).data =  arr.get(i).data;
        }
        if (GameScr.isChangeUI) {
            GameScr.isChangeUI = false;
            notifyDataSetChanged();
        }
    }
}

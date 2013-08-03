package com.example.Thoth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 19.07.13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class DispositionActivity extends Activity {
    List<Integer> cards;

    public static enum Layouts {
        Question,
        Seven,
        Celts,
        ModCelts,
        Relations,
        Balance,
        Common
    }

    public Layouts layout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        String message = intent.getStringExtra("Layout");

        cards = new ArrayList<Integer>();
        for (int i=0; i < 78; i++)
            cards.add(i);

        if (message.equals("Question")) {
            setContentView(R.layout.layout_question);
            createQuestion();
        } else if (message.equals("Seven")) {
            setContentView(R.layout.layout_seven);
            createSeven();
        } else if (message.equals("Celtic")) {
            setContentView(R.layout.layout_celticcross);
            createCeltic();
        } else if (message.equals("Modified")) {
            setContentView(R.layout.layout_celticphys);
            createModified();
        } else if (message.equals("Centers")) {
            setContentView(R.layout.layout_centers);
            createCenters();
        } else if (message.equals("Relations")) {
            setContentView(R.layout.layout_relations);
            createRelations();
        } else if (message.equals("Balance")) {
            setContentView(R.layout.layout_balance);
            createBalance();
        }

        super.onCreate(savedInstanceState);
    }

    private void createBalance() {
        List<Integer> left = new ArrayList<Integer>(),
                right = new ArrayList<Integer>(),
                united = new ArrayList<Integer>(),
                disposition = new ArrayList<Integer>();
        if (Utils.LastBalanceDisposition == null) {
            cards = shuffleCards(cards);

            Random random = new Random();
            int split = random.nextInt(78);
            List<Integer> top = cards.subList(0, split);
            List<Integer> bottom = cards.subList(split, 78);

            int choice = random.nextInt(2);
            switch (choice)  {
                case 0:
                    left = top;
                    right = bottom;
                    break;
                case 1:
                    left = bottom;
                    right = top;
                    break;
                default:
                    break;
            }

            left = shuffleCards(left);
            right = shuffleCards(right);

            disposition.add(left.get(0));
            disposition.add(left.get(left.size()-1));
            disposition.add(right.get(0));
            disposition.add(right.get(right.size()-1));

            united.addAll(left.subList(1,left.size()-2));
            united.addAll(right.subList(1,left.size()-2));

            List<Integer> total = new ArrayList<Integer>();
            int ch = random.nextInt(united.size());
            total.add(united.remove(ch));
            total.addAll(disposition);
            ch = random.nextInt(united.size());
            total.add(united.remove(ch));
            disposition = total;
        }
        else {
            disposition.addAll(Utils.LastBalanceDisposition);
        }

        Utils.LastBalanceDisposition = disposition;

        for (int i=0;i<6;i++) {
            final int cardID = disposition.get(i);
            ImageView image = (ImageView) findViewById(getResources().getIdentifier("imageView" + String.format("%02d", i + 1),
                    "id", getPackageName()));
            String card = Utils.GetResourceName(Utils.GetCardCode(disposition.get(i)));
            int drawableID = getResources().getIdentifier("thumb_" + card, "drawable", getPackageName());
            image.setImageResource(drawableID);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                    intent.putExtra("CardName", String.valueOf(cardID));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    private void createRelations() {
        List<Integer> disposition = new ArrayList<Integer>();
        if (Utils.LastRelationsDisposition == null) {
            cards = shuffleCards(cards);

            Random random = new Random();
            for (int i=0;i<6;i++) {
                int id =random.nextInt(7);
                disposition.add(cards.get(id));
                cards.remove(id);
            }
            disposition = shuffleCards(disposition);
        }
        else {
            disposition.addAll(Utils.LastRelationsDisposition);
        }

        Utils.LastRelationsDisposition = disposition;

        for (int i=0;i<6;i++) {
            final int cardID = disposition.get(i);
            ImageView image = (ImageView) findViewById(getResources().getIdentifier("imageView" + String.format("%02d", i + 1),
                    "id", getPackageName()));
            String card = Utils.GetResourceName(Utils.GetCardCode(disposition.get(i)));
            int drawableID = getResources().getIdentifier("thumb_" + card, "drawable", getPackageName());
            image.setImageResource(drawableID);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                    intent.putExtra("CardName", String.valueOf(cardID));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    private void createCenters() {
        List<Integer> disposition = new ArrayList<Integer>();
        if (Utils.LastCentersDisposition == null) {
            cards = shuffleCards(cards);

            Random random = new Random();
            for (int i=0;i<7;i++) {
                int id =random.nextInt(7);
                disposition.add(cards.get(id));
                cards.remove(id);
            }
            disposition = shuffleCards(disposition);
        }
        else {
            disposition.addAll(Utils.LastCentersDisposition);
        }

        Utils.LastCentersDisposition = disposition;

        for (int i=0;i<7;i++) {
            final int cardID = disposition.get(i);
            ImageView image = (ImageView) findViewById(getResources().getIdentifier("imageView" + String.valueOf(i+1),
                    "id", getPackageName()));
            String card = Utils.GetResourceName(Utils.GetCardCode(disposition.get(i)));
            int drawableID = getResources().getIdentifier("thumb_" + card, "drawable", getPackageName());
            image.setImageResource(drawableID);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                    intent.putExtra("CardName", String.valueOf(cardID));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    private void createModified() {
        List<Integer> disposition = new ArrayList<Integer>();
        if (Utils.LastModifiedDisposition == null) {
            Random random = new Random();
            List<Integer> top, middle, bottom, chosenSlice = new ArrayList<Integer>();
            for (int i=0;i<3;i++) {
                int split1 = random.nextInt(78);
                int split2 = random.nextInt(78 - split1) + split1;

                cards = shuffleCards(cards);

                top = cards.subList(0, split1);
                middle = cards.subList(split1, split2);
                bottom = cards.subList(split2, 78);

                cards = new ArrayList<Integer>();
                cards.addAll(bottom);
                cards.addAll(middle);
                cards.addAll(top);

                int choice = random.nextInt(3);
                switch (choice)  {
                    case 0:
                        chosenSlice = top;
                        break;
                    case 1:
                        chosenSlice = middle;
                        break;
                    case 2:
                        chosenSlice = bottom;
                        break;
                    default:
                        break;
                }
            }

            disposition = shuffleCards(chosenSlice);
        }
        else {
            disposition.addAll(Utils.LastModifiedDisposition);
        }

        Utils.LastModifiedDisposition = disposition;

        for (int i=0;i<10;i++) {
            final int cardID = disposition.get(i);
            ImageView image = (ImageView) findViewById(getResources().getIdentifier("imageView" + String.format("%02d", i+1),
                    "id", getPackageName()));
            String card = Utils.GetResourceName(Utils.GetCardCode(disposition.get(i)));
            int drawableID = getResources().getIdentifier("thumb_" + card, "drawable", getPackageName());
            image.setImageResource(drawableID);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                    intent.putExtra("CardName", String.valueOf(cardID));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    private void createSeven() {
        List<Integer> disposition = new ArrayList<Integer>();
        if (Utils.LastSevenDisposition == null) {
            cards = shuffleCards(cards);

            Random random = new Random();
            for (int i=0;i<7;i++) {
                int id =random.nextInt(7);
                disposition.add(cards.get(id));
                cards.remove(id);
            }
            disposition = shuffleCards(disposition);
        }
        else {
            disposition.addAll(Utils.LastSevenDisposition);
        }

        Utils.LastSevenDisposition = disposition;

        for (int i=0;i<7;i++) {
            final int cardID = disposition.get(i);
            ImageView image = (ImageView) findViewById(getResources().getIdentifier("imageView" + String.valueOf(i),
                    "id", getPackageName()));
            String card = Utils.GetResourceName(Utils.GetCardCode(disposition.get(i)));
            int drawableID = getResources().getIdentifier("thumb_" + card, "drawable", getPackageName());
            image.setImageResource(drawableID);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                    intent.putExtra("CardName", String.valueOf(cardID));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    private void createCeltic() {
        List<Integer> disposition = new ArrayList<Integer>();
        if (Utils.LastCelticDisposition == null) {
            Random random = new Random();
            List<Integer> top, middle, bottom, chosenSlice = new ArrayList<Integer>();
            for (int i=0;i<3;i++) {
                int split1 = random.nextInt(78);
                split1 = split1 > 10 ? split1 : 10;
                int split2 = random.nextInt(78 - split1) + split1;
                split2 = split2 - split1 > 10 ? split2 : split1 + 10;

                cards = shuffleCards(cards);

                top = cards.subList(0, split1);
                middle = cards.subList(split1, split2);
                bottom = cards.subList(split2, 78);

                cards = new ArrayList<Integer>();
                cards.addAll(bottom);
                cards.addAll(middle);
                cards.addAll(top);

                int choice = random.nextInt(3);
                switch (choice)  {
                    case 0:
                        chosenSlice = top;
                        break;
                    case 1:
                        chosenSlice = middle;
                        break;
                    case 2:
                        chosenSlice = bottom;
                        break;
                    default:
                        break;
                }
            }

            disposition = shuffleCards(chosenSlice);
        }
        else {
            disposition.addAll(Utils.LastCelticDisposition);
        }

        Utils.LastCelticDisposition = disposition;

        for (int i=0;i<10;i++) {
            final int cardID = disposition.get(i);
            ImageView image = (ImageView) findViewById(getResources().getIdentifier("imageView" + String.format("%02d", i+1),
                    "id", getPackageName()));
            String card = Utils.GetResourceName(Utils.GetCardCode(disposition.get(i)));
            int drawableID = getResources().getIdentifier("thumb_" + card, "drawable", getPackageName());
            image.setImageResource(drawableID);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                    intent.putExtra("CardName", String.valueOf(cardID));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }


    private void createQuestion() {
        ArrayList<Integer> disposition = new ArrayList<Integer>();
        if (Utils.LastQuestionDisposition == null) {
            cards = shuffleCards(cards);

            Random random = new Random();
            int split = random.nextInt(78);
            List<Integer> top = cards.subList(0, split);
            List<Integer> bottom = cards.subList(split, 78);

            top = shuffleCards(top);
            bottom = shuffleCards(bottom);

            disposition.add(bottom.get(bottom.size()-1));
            disposition.add(top.get(top.size()-1));
            disposition.add(top.get(0));
            disposition.add(bottom.get(0));
        }
        else {
            disposition.addAll(Utils.LastQuestionDisposition);
        }

        Utils.LastQuestionDisposition = disposition;

        ImageView image1 = (ImageView) findViewById(R.id.imageView1);
        ImageView image2 = (ImageView) findViewById(R.id.imageView);
        ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        ImageView image4 = (ImageView) findViewById(R.id.imageView2);

        String card1 = Utils.GetResourceName(Utils.GetCardCode(disposition.get(0)));
        String card2 = Utils.GetResourceName(Utils.GetCardCode(disposition.get(1)));
        String card3 = Utils.GetResourceName(Utils.GetCardCode(disposition.get(2)));
        String card4 = Utils.GetResourceName(Utils.GetCardCode(disposition.get(3)));

        int drawableID1 = getResources().getIdentifier("thumb_" + card1, "drawable", getPackageName());
        int drawableID2 = getResources().getIdentifier("thumb_" + card2, "drawable", getPackageName());
        int drawableID3 = getResources().getIdentifier("thumb_" + card3, "drawable", getPackageName());
        int drawableID4 = getResources().getIdentifier("thumb_" + card4, "drawable", getPackageName());

        image1.setImageResource(drawableID1);
        image2.setImageResource(drawableID2);
        image3.setImageResource(drawableID3);
        image4.setImageResource(drawableID4);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(Utils.LastQuestionDisposition.get(0)));
                view.getContext().startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(Utils.LastQuestionDisposition.get(1)));
                view.getContext().startActivity(intent);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(Utils.LastQuestionDisposition.get(2)));
                view.getContext().startActivity(intent);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(Utils.LastQuestionDisposition.get(3)));
                view.getContext().startActivity(intent);
            }
        });
    }

    private List<Integer> shuffleCards(List<Integer> cardsArray) {
        Random random = new Random();
        for (int i = cardsArray.size() - 1; i >= 0; i--) {
            int j = random.nextInt(i + 1);

            Integer card = cardsArray.get(i);
            cardsArray.set(i, cardsArray.get(j));
            cardsArray.set(j, card);
        }
        return cardsArray;
    }
}
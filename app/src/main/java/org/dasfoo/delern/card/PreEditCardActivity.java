package org.dasfoo.delern.card;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.dasfoo.delern.R;
import org.dasfoo.delern.models.Card;
import org.dasfoo.delern.util.LogUtil;

/**
 * Activity that shows the card before it is being edited.
 * TODO(ksheremet): use existing showCardActivity for that?
 */
public class PreEditCardActivity extends AppCompatActivity {

    /**
     * IntentExtra R.string title of the activity.
     */
    public static final String LABEL = "label";

    /**
     * IntentExtra deck ID that this card belongs to.
     */
    public static final String DECK_ID = "deckId";

    /**
     * IntentExtra card ID that is being edited.
     */
    public static final String CARD_ID = "cardId";

    private static final String TAG = LogUtil.tagFor(PreEditCardActivity.class);

    private String mDeckId;
    private Card mCard;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_edit_card_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        String label = intent.getStringExtra(LABEL);
        mDeckId = intent.getStringExtra(DECK_ID);
        String cardId = intent.getStringExtra(CARD_ID);

        this.setTitle(label);
        final TextView frontPreview = (TextView) findViewById(R.id.textFrontPreview);
        final TextView backPreview = (TextView) findViewById(R.id.textBackPreview);

        Query query = Card.getCardById(mDeckId, cardId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Card card;
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    Log.v(TAG, cardSnapshot.toString());
                    card = cardSnapshot.getValue(Card.class);
                    card.setcId(cardSnapshot.getKey());
                    Log.v(TAG, card.toString());
                    frontPreview.setText(card.getFront());
                    backPreview.setText(card.getBack());
                    mCard = card;
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.v(TAG, databaseError.getMessage());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                editCardActivityStart();
            }
        });
    }

    private void editCardActivityStart() {
        Intent intentEdit = new Intent(this, AddEditCardActivity.class);
        intentEdit.putExtra(AddEditCardActivity.DECK_ID, mDeckId);
        intentEdit.putExtra(AddEditCardActivity.LABEL, R.string.edit);
        intentEdit.putExtra(AddEditCardActivity.CARD, mCard);
        startActivity(intentEdit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_card_menu, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_card_menu:
                deleteCard(mDeckId, mCard);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void deleteCard(final String deckId, final Card card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_card_warning);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                Card.deleteCardFromDeck(deckId, card);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
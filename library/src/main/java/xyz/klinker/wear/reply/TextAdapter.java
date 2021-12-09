package xyz.klinker.wear.reply;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import xyz.klinker.wear.reply.ResourceTable;

public class TextAdapter extends BaseItemProvider {

    private AbilitySlice mAbilitySlice;
    private CharSequence[] mItemList;
    private ItemClickListener mItemClickListener;

    public TextAdapter(CharSequence[] itemList, AbilitySlice abilitySlice, ItemClickListener itemClickListener ) {
        mAbilitySlice = abilitySlice;
        mItemList = itemList;
        mItemClickListener = itemClickListener;
    }

    @Override
    public int getCount() {
        return mItemList.length;
    }

    @Override
    public Object getItem(int position) {
        return mItemList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component component, ComponentContainer componentContainer) {
        Component rootView = LayoutScatter.getInstance(mAbilitySlice)
                .parse(ResourceTable.Layout_wearreply_item_text, null,false);
        Text itemName = (Text) rootView.findComponentById(ResourceTable.Id_text);
        itemName.setText(mItemList[position].toString());
        itemName.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mItemClickListener.onItemClick(position);
            }
        });

        return rootView;
    }
}

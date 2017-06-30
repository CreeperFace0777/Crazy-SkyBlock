package team.crazynetwork.raids.commands.shop;

import org.bukkit.inventory.ItemStack;

import team.crazynetwork.raids.commands.shop.IconMenu.OptionClickEvent;

public class ShopListener implements IconMenu.OptionClickEventHandler {
	String[] actions;
	String[] actionTarget;
	ItemStack[] itemStack;
	
	@Override
	public void onOptionClick(OptionClickEvent event) {
		if (actions[event.getPosition()] == null){
			return;
		}
		if (actionTarget[event.getPosition()] == null){
			return;
		}
		if (actions[event.getPosition()].equalsIgnoreCase("openSub")){
			event.getPlayer().closeInventory();
			new ShopCommand().openInv(event.getPlayer(),actionTarget[event.getPosition()]);
		} else {
			if (itemStack[event.getPosition()] == null){
				return;
			}
			int price;
			try {
				price = Integer.parseInt(actionTarget[event.getPosition()]);
			} catch (Exception e) {
				//Ignore any errors. Price may not be int.
				return;
			}
			new ShopCommand().openPurchase(event.getPlayer(),this.itemStack[event.getPosition()],price,1);
		}
	}
	
	public void setAction(int count,String s){
		this.actions[count] = s;
	}
	
	public void setTarget(int count,String s){
		this.actionTarget[count] = s;
	}
	
	public void setItemStack(int count,ItemStack s){
		this.itemStack[count] = s;
	}
}

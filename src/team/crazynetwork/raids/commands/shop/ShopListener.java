package team.crazynetwork.raids.commands.shop;

import team.crazynetwork.raids.commands.shop.IconMenu.OptionClickEvent;

public class ShopListener implements IconMenu.OptionClickEventHandler {
	String[] actions;
	String[] actionTarget;
	
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
			//Item Purchase to be implemented.
		}
	}
	
	public void setAction(int count,String s){
		this.actions[count] = s;
	}
	
	public void setTarget(int count,String s){
		this.actionTarget[count] = s;
	}
}

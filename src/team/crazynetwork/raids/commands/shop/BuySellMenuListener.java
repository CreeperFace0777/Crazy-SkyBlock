package team.crazynetwork.raids.commands.shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.crazynetwork.raids.Island;
import team.crazynetwork.raids.SkyBlockRaids;
import team.crazynetwork.raids.commands.shop.IconMenu.OptionClickEvent;

public class BuySellMenuListener implements IconMenu.OptionClickEventHandler {
	
	int price;
	int purchaseAmount;
	Player player;
	ItemStack item;
	
	public BuySellMenuListener(int price, int purchaseAmount, Player player, ItemStack item){
		this.price = price;
		this.purchaseAmount = purchaseAmount;
		this.player = player;
		this.item = item;
	}
	
	@Override
	public void onOptionClick(OptionClickEvent event) {
		int purchaseAmount = this.purchaseAmount;
		if (event.getPosition() < 36){
			switch (event.getPosition()){
				case 27:
					purchaseAmount = 1;
					break;
				case 28:
					purchaseAmount = purchaseAmount - 10;
					break;
				case 29:
					purchaseAmount = purchaseAmount - 1;
					break;
				case 35:
					purchaseAmount = 64;
					break;
				case 34:
					purchaseAmount = purchaseAmount + 10;
					break;
				case 33:
					purchaseAmount = purchaseAmount + 1;
					break;
				default:
					break;
			}
			if (purchaseAmount > 64){
				purchaseAmount = 64;
			}
			if (purchaseAmount < 1){
				purchaseAmount = 1;
			}
			new ShopCommand().openPurchase(player, item, price, purchaseAmount);
		} else {
			switch (event.getPosition()){
			case 48:
				Island i = SkyBlockRaids.getPlugin().findIsland(player);
				if (i.getBalance() >= (price * purchaseAmount)){
					i.setBalance(i.getBalance() - (price*purchaseAmount));
					break;
				} else {
					break;
				}
			case 49:
				player.closeInventory();
				break;
			case 50:
				if (player.getInventory().contains(item,purchaseAmount)){
					item.setAmount(purchaseAmount);
					player.getInventory().removeItem(item);
					break;
				} else {
					break;
				}
			}
		}
	}
	
	
}

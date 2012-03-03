package com.medphone.aliens;

public class LeftArmDemo extends AliensProcess {

	public String getName() {
		return "LeftArmDemo";
	}

	public void event() {
		switch (stage) {
		case 0:
			a().cancelProcess(getName());
			addNotification("Пуля обожгла мне левую руку, " +
					"но не причинила существенного вреда."); 
			addNotification("А вот костюм безнадёжно испорчен :(");
			setAttr("status", 
					"оплакиваю костюм: тут по идее должно быть очень детальное " +
					"описание костюма - lorem ipsum dolor sit amet, " +
					"consectetur adipisicing elit, sed do eiusmod tempor " +
					"incididunt ut labore et dolore magna aliqua. Ut enim ad " +
					"minim veniam, quis nostrud exercitation ullamco laboris " +
					"nisi ut aliquip ex ea commodo consequat. Duis aute irure " +
					"dolor in reprehenderit in voluptate velit esse cillum " +
					"dolore eu fugiat nulla pariatur. Excepteur sint occaecat " +
					"cupidatat non proident, sunt in culpa qui officia deserunt " +
					"mollit anim id est laborum; это нужно чтобы удостовериться " +
					"что прокрутка длинных текстов работает корректно");
			stage = 1;
			schedule(40+a().rand(10));
			break;
		}
	}

}

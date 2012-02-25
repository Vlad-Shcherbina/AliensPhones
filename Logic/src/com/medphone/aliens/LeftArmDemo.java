package com.medphone.aliens;

public class LeftArmDemo extends AliensProcess {

	public String get_name() {
		return "LeftArmDemo";
	}

	public void event() {
		switch (stage) {
		case 0:
			a().cancel_process(get_name());
			add_notification("Пуля обожгла мне левую руку, " +
					"но не причинила существенного вреда."); 
			add_notification("А вот костюм безнадёжно испорчен :(");
			set_attr("status", 
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

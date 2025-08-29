package patterns.builder;

public class MenuItem {
    private String name;
    private int price;
    private boolean cheese;
    private boolean sauce;

    private MenuItem(MenuItemBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.cheese = builder.cheese;
        this.sauce = builder.sauce;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return name +
                (cheese ? " + Cheese" : "") +
                (sauce ? " + Sauce" : "") +
                " - ₹" + price;
    }

    public static class MenuItemBuilder {
        private String name;
        private int price;
        private boolean cheese;
        private boolean sauce;

        public MenuItemBuilder(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public MenuItemBuilder addCheese() {
            this.cheese = true;
            this.price += 30;
            return this;
        }

        public MenuItemBuilder addSauce() {
            this.sauce = true;
            this.price += 20;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(this);
        }
    }
}

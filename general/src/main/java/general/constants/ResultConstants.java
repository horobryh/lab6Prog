package general.constants;

public enum ResultConstants {
    BOOLEAN("Результат - логическое значение"),
    INTEGER("Результат - Integer"),
    FLOAT("Результат - Float"),
    LONG("Результат - Long"),
    DOUBLE("Результат - Double"),
    STRING("Результат - строка"),
    BASEMODEL("Результат - реализация базовой модели"),
    FIRSTCHECK("Результат первой проверки"),
    OBJECT("Результат - объект"),
    DATE("Результат - дата");
    private String description;
    ResultConstants(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
}

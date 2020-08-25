package love.korni.studydiscordbot.config.converter;

public interface Converter <T, F> {

    F convertTo(T source);

    T convertFrom(F source);
}

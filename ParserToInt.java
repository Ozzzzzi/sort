public class ParserToInt implements DataParser<Integer>{
    @Override
    public Integer parse(String line) {
        return Integer.parseInt(line);
    }
}

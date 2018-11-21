package com.rplsd.zadwal.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ZadwalDriver {
    public void start(String in) {
        CharStream charStream = new ANTLRInputStream(in);
        ZadwalLexer lexer = new ZadwalLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        ZadwalParser parser = new ZadwalParser(commonTokenStream);
        ParseTree tree = parser.eval(); // parse the content and get the tree
        ZadwalWalker listener = new ZadwalWalker();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
    }
}

/**
 * Starschema Big Query JDBC Driver
 * Copyright (C) 2012, Starschema Ltd.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.slemma.jdbc.list;

import com.slemma.jdbc.JdbcGrammarParser;


/**
 * This class extends the basic Node
 * we store a simple string in it
 * 
 * @author Balazs Gunics, Attila Horvath
 */
public class StringLiteral extends Node {

    private final boolean quoted;
    /**
     * to Store the Stringliterals
     * 
     * @param string
     *            - this will be stored at the data field
     */
    public StringLiteral(String string) {
        this(string, false);
    }

    public StringLiteral(String string, boolean quoted) {
        this.tokenType = JdbcGrammarParser.STRINGLIT;
        this.tokenName = JdbcGrammarParser.tokenNames[this.tokenType];
        this.logger.debug("BUILDING " + this.tokenName);
        this.data = string;
        this.quoted = quoted;
    }

    @Override
    public String toPrettyString(int level) {
        //since we store the Ints as String, we'll try to parse them to Int,
        if (!this.quoted) {
            try{
                Integer.parseInt(this.data);
            }
            catch (NumberFormatException nfe){
                return "'" + this.data + "'";
            }
        }
        return this.data;
    }
}

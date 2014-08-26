/*************************************************************************
 *
 * Copyright 2009 by bBreak Systems.
 *
 * ExCella Trans - Excelファイルを利用したデータ移行支援ツール
 *
 * $Id: TruncateCascadeParserTest.java 35 2009-07-02 08:02:26Z yuta-takahashi $
 * $Revision: 35 $
 *
 * This file is part of ExCella Trans.
 *
 * ExCella Trans is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * ExCella Trans is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the COPYING.LESSER file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with ExCella Trans.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>
 * for a copy of the LGPLv3 License.
 *
 ************************************************************************/
package org.bbreak.excella.trans.tag.sheet2sql;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bbreak.excella.core.exception.ParseException;
import org.bbreak.excella.trans.WorkbookTest;
import org.junit.Test;

/**
 * TruncateCascadeParserテストクラス
 * 
 * @since 1.0
 */
public class TruncateCascadeParserTest extends WorkbookTest {

    /**
     * コンストラクタ
     * 
     * @param version Excelファイルのバージョン
     */
    public TruncateCascadeParserTest( String version) {
        super( version);
    }

    @Test
    public final void testTruncateCascadeParser() throws ParseException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt( 0);
        TruncateCascadeParser truncateCascadeParser = new TruncateCascadeParser( "@TruncateCascade");
        Cell tagCell = null;
        Object data = null;
        List<String> list = null;

        // ===============================================
        // parse( Sheet sheet, Cell tagCell, Object data)
        // ===============================================
        // No.1 パラメータ無し
        tagCell = sheet.getRow( 10).getCell( 0);
        list = truncateCascadeParser.parse( sheet, tagCell, data);
        assertEquals( "truncate table_name1 cascade;", list.get( 0));
        assertEquals( "truncate table_name2 cascade;", list.get( 1));
        assertEquals( "truncate   cascade;", list.get( 2));
        assertEquals( 3, list.size());

        // No.2 パラメータ有り、null行を含む
        tagCell = sheet.getRow( 2).getCell( 4);
        list = truncateCascadeParser.parse( sheet, tagCell, data);
        assertEquals( "truncate table_name3 cascade;", list.get( 0));
        assertEquals( "truncate table_name4 cascade;", list.get( 1));
        assertEquals( "truncate table_name5 cascade;", list.get( 2));
        assertEquals( 3, list.size());

        // No.3 パラメータ有り、nullセルを含む
        tagCell = sheet.getRow( 8).getCell( 12);
        list = truncateCascadeParser.parse( sheet, tagCell, data);
        assertEquals( "truncate table_name6 cascade;", list.get( 0));
        assertEquals( "truncate table_name7 cascade;", list.get( 1));
        assertEquals( 2, list.size());

        // No.4 デフォルトタグ
        TruncateCascadeParser truncateCascadeParser2 = new TruncateCascadeParser();
        tagCell = sheet.getRow( 8).getCell( 22);
        list = truncateCascadeParser2.parse( sheet, tagCell, data);
        assertEquals( "truncate table_name8 cascade;", list.get( 0));
        assertEquals( "truncate table_name9 cascade;", list.get( 1));
        assertEquals( 2, list.size());

    }
}

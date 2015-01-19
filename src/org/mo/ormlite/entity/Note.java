package org.mo.ormlite.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by moziqi on 2015/1/18 0018.
 */
@DatabaseTable(tableName = "tb_note")
public class Note extends BaseColumn {
    private String title;
    private String content;
    private String type;
    @DatabaseField()
    private User mUser;
}

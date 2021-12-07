package de.vmeerkamp.animalshelter.logic.db;

/**
 * Diese Klasse dient dazu alle benoetigten SQL-Schluesselwoerter
 * gekapselt und zentral zur Verfuegung zu stellen.
 */

public abstract class ASqlKeyWords {
	
	//region 0. Chars
	
	protected static final String CHAR_COMMA                    = ", ";
	protected static final String CHAR_SINGLE_QUOTATION_MARK    = "'";
	protected static final String CHAR_QUESTION_MARK            = "?";
	protected static final String CHAR_QUESTION_MARK_WITH_COMMA = CHAR_QUESTION_MARK + CHAR_COMMA;
	protected static final String CHAR_SEMICOLON                = ";";
	protected static final String CHAR_OPEN_BRACKET             = "(";
	protected static final String CHAR_CLOSE_BRACKET            = ")";
	protected static final String CHAR_CLOSE_BRACKET_SEMICOLON  = CHAR_CLOSE_BRACKET + CHAR_SEMICOLON;
	//endregion
	
	//region 1. Datatypes
	
	protected static final String DATA_TYPE_INTEGER           = " INTEGER ";
	protected static final String DATA_TYPE_INTEGER_WITH_COMMA = " INTEGER" + CHAR_COMMA;
	
	protected static final String DATA_TYPE_DOUBLE           = " DOUBLE ";
	protected static final String DATA_TYPE_DOUBLE_WITH_COMMA = " DOUBLE" + CHAR_COMMA;
	
	protected static final String DATA_TYPE_VARCHAR           = " VARCHAR ";
	protected static final String DATA_TYPE_VARCHAR_WITH_COMMA = " VARCHAR" + CHAR_COMMA;
	
	protected static final String DATA_TYPE_TEXT           = " TEXT ";
	protected static final String DATA_TYPE_TEXT_WITH_COMMA = " TEXT" + CHAR_COMMA;
	
	protected static final int DATA_TYPE_BOOLEAN_FALSE = 0;
	protected static final int DATA_TYPE_BOOLEAN_TRUE  = 1;
	//endregion
	
	//region 2. Operators
	
	protected static final String SET_WITH_BLANKS    = " SET ";
	protected static final String AND_WITH_BLANKS    = " AND ";
	protected static final String VALUES_WITH_BLANKS = " VALUES ";
	protected static final String EQUALS_WITH_BLANKS = " = ";
	//endregion
	
	//region 3. Conditions
	
	protected static final String WHERE_WITH_BLANKS = " WHERE ";
	//endregion
	
	//region 4. Table actions
	
	protected static final String SELECT_WITH_BLANK    = "SELECT ";
	protected static final String FROM_WITH_BLANKS     = " FROM ";
	protected static final String SELECT_ALL_DATA_FROM = SELECT_WITH_BLANK + "*" + FROM_WITH_BLANKS;
	
	protected static final String INSERT_INTO_WITH_BLANK = "INSERT INTO ";
	protected static final String UPDATE_WITH_BLANK      = "UPDATE ";
	protected static final String DELETE_FROM_WITH_BLANK = "DELETE" + FROM_WITH_BLANKS;
	//endregion
}
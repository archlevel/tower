package com.tower.service.dao.util;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.Configuration;

import com.alibaba.druid.wall.WallCheckResult;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallProvider;
import com.alibaba.druid.wall.spi.MySqlWallProvider;
import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigFileTypeDict;
import com.tower.service.dao.ibatis.IBatisDAOException;
import com.tower.service.exception.DataAccessException;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class SqlFireWall extends PrefixPriorityConfig {

	private Boolean check = true;
	
	private WallConfig wallConfig = new WallConfig();
	
	private WallProvider provider = new MySqlWallProvider(wallConfig);

	public static String WALL_CODE = "BlackWall";

	private static final Logger minitorLogger = LoggerFactory
			.getLogger(SqlFireWall.class);
	private static SqlFireWall instance;

	public static synchronized SqlFireWall getInstance() {
		if (instance == null) {
			instance = new SqlFireWall();
		}
		return instance;
	}

	@Override
	@PostConstruct
	public void init() {
		this.setFileName(System.getProperty(ACCESS_CONTROL_CONFIG_FILE,
				DEFAULT_ACCESS_CONTROL_CONFIG_NAME));
		this.setType(ConfigFileTypeDict.XML);
		super.init();
		this.build(this.getConfig());
	}

	protected void build(Configuration config) {

		String prefix_ = this.getPrefix();

		check = config.getBoolean(prefix_
				+ "doSqlFireWall", true);
		
		wallConfig.setNoneBaseStatementAllow(config.getBoolean(prefix_
				+ "noneBaseStatementAllow", true));
		wallConfig
				.setCallAllow(config.getBoolean(prefix_ + "callAllow", false));
		wallConfig.setSelelctAllow(config.getBoolean(prefix_ + "selelctAllow",
				true));
		wallConfig.setSelectIntoAllow(config.getBoolean(prefix_
				+ "selectIntoAllow", false));
		wallConfig.setSelectIntoOutfileAllow(config.getBoolean(prefix_
				+ "selectIntoOutfileAllow", false));
		wallConfig.setSelectWhereAlwayTrueCheck(config.getBoolean(prefix_
				+ "selectWhereAlwayTrueCheck", true));

		wallConfig.setSelectHavingAlwayTrueCheck(config.getBoolean(prefix_
				+ "selectHavingAlwayTrueCheck", true));

		wallConfig.setSelectUnionCheck(config.getBoolean(prefix_
				+ "selectUnionCheck", true));

		wallConfig.setSelectMinusCheck(config.getBoolean(prefix_
				+ "selectMinusCheck", true));

		wallConfig.setSelectExceptCheck(config.getBoolean(prefix_
				+ "selectExceptCheck", true));

		wallConfig.setSelectIntersectCheck(config.getBoolean(prefix_
				+ "selectIntersectCheck", true));

		// ----------------------

		wallConfig.setCreateTableAllow(config.getBoolean(prefix_
				+ "createTableAllow", false));
		wallConfig.setDropTableAllow(config.getBoolean(prefix_
				+ "dropTableAllow", false));
		wallConfig.setAlterTableAllow(config.getBoolean(prefix_
				+ "alterTableAllow", false));
		wallConfig.setRenameTableAllow(config.getBoolean(prefix_
				+ "renameTableAllow", false));
		wallConfig.setHintAllow(config.getBoolean(prefix_ + "hintAllow", true));
		wallConfig.setLockTableAllow(config.getBoolean(prefix_
				+ "lockTableAllow", false));
		wallConfig.setStartTransactionAllow(config.getBoolean(prefix_
				+ "startTransactionAllow", true));
		// ----------------------
		wallConfig.setConditionAndAlwayTrueAllow(config.getBoolean(prefix_
				+ "conditionAndAlwayTrueAllow", false));
		wallConfig.setConditionAndAlwayFalseAllow(config.getBoolean(prefix_
				+ "conditionAndAlwayFalseAllow", false));
		wallConfig.setConditionDoubleConstAllow(config.getBoolean(prefix_
				+ "conditionDoubleConstAllow", false));
		wallConfig.setConditionLikeTrueAllow(config.getBoolean(prefix_
				+ "conditionLikeTrueAllow", false));
		// ----------------------

		wallConfig.setSelectAllColumnAllow(config.getBoolean(prefix_
				+ "selectAllColumnAllow", false));
		wallConfig.setDeleteAllow(config.getBoolean(prefix_ + "deleteAllow",
				false));
		wallConfig.setDeleteWhereAlwayTrueCheck(config.getBoolean(prefix_
				+ "deleteWhereAlwayTrueCheck", true));
		wallConfig.setDeleteWhereNoneCheck(config.getBoolean(prefix_
				+ "deleteWhereNoneCheck", true));
		// ----------------------
		wallConfig.setUpdateAllow(config.getBoolean(prefix_ + "updateAllow",
				true));
		wallConfig.setUpdateWhereAlayTrueCheck(config.getBoolean(prefix_
				+ "UpdateWhereAlayTrueCheck", true));
		wallConfig.setUpdateWhereNoneCheck(config.getBoolean(prefix_
				+ "UpdateWhereNoneCheck", true));
		// ----------------------
		wallConfig.setInsertAllow(config.getBoolean(prefix_ + "insertAllow",
				true));
		wallConfig.setMergeAllow(config
				.getBoolean(prefix_ + "mergeAllow", true));
		wallConfig.setMinusAllow(config
				.getBoolean(prefix_ + "minusAllow", true));
		wallConfig.setIntersectAllow(config.getBoolean(prefix_
				+ "intersectAllow", true));
		wallConfig.setReplaceAllow(config.getBoolean(prefix_ + "replaceAllow",
				true));
		wallConfig.setSetAllow(config.getBoolean(prefix_ + "setAllow", true));
		wallConfig.setCommitAllow(config.getBoolean(prefix_ + "commitAllow",
				true));
		wallConfig.setRollbackAllow(config.getBoolean(
				prefix_ + "rollbackAllow", true));
		wallConfig.setUseAllow(config.getBoolean(prefix_ + "useAllow", true));

		wallConfig.setMultiStatementAllow(config.getBoolean(prefix_
				+ "multiStatementAllow", true));
		wallConfig.setTruncateAllow(config.getBoolean(
				prefix_ + "truncateAllow", false));

		wallConfig.setCommentAllow(config.getBoolean(prefix_ + "commentAllow",
				true));
		wallConfig.setStrictSyntaxCheck(config.getBoolean(prefix_
				+ "strictSyntaxCheck", true));
		wallConfig.setConstArithmeticAllow(config.getBoolean(prefix_
				+ "constArithmeticAllow", true));
		wallConfig.setLimitZeroAllow(config.getBoolean(prefix_
				+ "limitZeroAllow", false));
		wallConfig.setDescribeAllow(config.getBoolean(
				prefix_ + "describeAllow", false));
		wallConfig
				.setShowAllow(config.getBoolean(prefix_ + "showAllow", false));
		wallConfig.setSchemaCheck(config.getBoolean(prefix_ + "schemaCheck",
				true));
		wallConfig.setTableCheck(config
				.getBoolean(prefix_ + "tableCheck", true));
		wallConfig.setFunctionCheck(config.getBoolean(
				prefix_ + "functionCheck", true));
		wallConfig.setObjectCheck(config.getBoolean(prefix_ + "objectCheck",
				true));
		wallConfig.setVariantCheck(config.getBoolean(prefix_ + "variantCheck",
				true));

		wallConfig.setMustParameterized(config.getBoolean(prefix_
				+ "mustParameterized", false));
		wallConfig.setDoPrivilegedAllow(config.getBoolean(prefix_
				+ "doPrivilegedAllow", false));

		wallConfig.setWrapAllow(config.getBoolean(prefix_ + "wrapAllow", true));
		wallConfig.setMetadataAllow(config.getBoolean(
				prefix_ + "metadataAllow", true));
		wallConfig.setConditionOpXorAllow(config.getBoolean(prefix_
				+ "conditionOpXorAllow", false));
		wallConfig.setConditionOpBitwseAllow(config.getBoolean(prefix_
				+ "conditionOpBitwseAllow", true));
		wallConfig.setCaseConditionConstAllow(config.getBoolean(prefix_
				+ "caseConditionConstAllow", false));
		wallConfig.setCompleteInsertValuesCheck(config.getBoolean(prefix_
				+ "completeInsertValuesCheck", false));

		wallConfig.setInsertValuesCheckSize(config.getInt(prefix_
				+ "insertValuesCheckSize", 3));

		provider.setBlackListEnable(check);

	}

	public boolean check(String sql) {
		if (check) {
			WallCheckResult result = provider.check(sql);
			if (!result.getViolations().isEmpty()) {
				String msString = result.getViolations().get(0).getMessage();
				minitorLogger.info(msString, sql);
				throw new DataAccessException(IBatisDAOException.MSG_1_0007,
						sql);
			}
		}
		return true;
	}
}

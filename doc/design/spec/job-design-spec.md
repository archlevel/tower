## job设计文档


<table>
	<tr>
        <td><font color="red">设计原则</font></td>
        <td colspan="3" width="80%">平滑上线【不停服务上线】、向下兼容</td>
    </tr>
    <tr>
        <td><font color="red">Job编号</font></td>
        <td colspan="3" width="80%"></td>
    </tr>
    <tr>
        <td><font color="red">Job名称</font></td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td><font color="red">技术负责人</font></td>
        <td width="30%"> </td>
        <td><font color="red">相关人</font></td>
        <td  width="30%"></td>
    </tr>
    <tr>
        <td>功能描述</td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td>业务级别<br/>[<br/>P1：影响全局<br/>P2：影响30%以上的用户 <br/>P3：影响30%以下的用户<br/>]</td>
        <td width="30%"></td>
        <td>运行时间级别<br/>[
        P1:全天<br/>
        P2:除凌晨0～2点的以外时间段<br/>]</td>
        <td width="30%"></td>
    </tr>
    <tr>
        <td><font color="red">上线检测验证方式【查看xxx表的status状态】</font></td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td>该服务器是否有监控</td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td>开始运行时间【eg ：每天的凌晨两点】</td>
        <td width="30%"></td>
        <td>预计运行结束时间【eg:每天的凌晨三点】</td>
        <td width="30%"></td>
    </tr>
    <tr>
        <td>预计运行时长【eg:1小时】</td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td><font color="red">业务监控方式【knowing图的地址,及报警策略】</font></td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td><font color="red">设计/技术方案【描述job实现的主要逻辑】</font></td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td>数据量级【1w~10w|10w~100w|100w以上】</td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td><font color="red">异常处理机制</font></td>
        <td colspan="3">eg:job重跑</td>
    </tr>
	<tr>
        <td>log文件路径【eg:/data/mogologs/xxx】</td>
        <td colspan="3"></td>
    </tr>
    <tr>
        <td>log文件清除方法【日志保留时长，超过这个时长是否采用物理删除】</td>
        <td colspan="3"></td>
    </tr>
</table>

#### [参考性能评估规范](performance-evaluation-spec.md)
#### [参考安全评估规范](security-evaluation-spec.md)
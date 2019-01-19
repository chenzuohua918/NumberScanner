package com.example.qrcode.model;

import android.text.TextUtils;

import com.example.qrcode.bean.ExpressCompany;

import java.util.ArrayList;
import java.util.List;

public class ExpressCompanyModel {
    private static ExpressCompanyModel instance;
    public List<ExpressCompany> mExpressCompanyList;

    private ExpressCompanyModel() {
        mExpressCompanyList = new ArrayList<>();
        loadData();
    }

    private static synchronized void syncInit() {
        if (null == instance) {
            instance = new ExpressCompanyModel();
        }
    }

    public static ExpressCompanyModel getInstance() {
        if (null == instance) {
            syncInit();
        }
        return instance;
    }

    public void loadData() {
        mExpressCompanyList.add(new ExpressCompany("aae", "aae全球专递"));
        mExpressCompanyList.add(new ExpressCompany("anjie", "安捷快递"));
        mExpressCompanyList.add(new ExpressCompany("anxindakuaidi", "安信达快递"));
        mExpressCompanyList.add(new ExpressCompany("biaojikuaidi", "彪记快递"));
        mExpressCompanyList.add(new ExpressCompany("bht", "bht"));
        mExpressCompanyList.add(new ExpressCompany("baifudongfang", "百福东方国际物流"));
        mExpressCompanyList.add(new ExpressCompany("coe", "中国东方（COE）"));
        mExpressCompanyList.add(new ExpressCompany("changyuwuliu", "长宇物流"));
        mExpressCompanyList.add(new ExpressCompany("datianwuliu", "大田物流"));
        mExpressCompanyList.add(new ExpressCompany("debangwuliu", "德邦物流"));
        mExpressCompanyList.add(new ExpressCompany("dhl", "dhl"));
        mExpressCompanyList.add(new ExpressCompany("dpex", "dpex"));
        mExpressCompanyList.add(new ExpressCompany("dsukuaidi", "d速快递"));
        mExpressCompanyList.add(new ExpressCompany("disifang", "递四方"));
        mExpressCompanyList.add(new ExpressCompany("ems", "EMS快递"));
        mExpressCompanyList.add(new ExpressCompany("fedex", "fedex（国外）"));
        mExpressCompanyList.add(new ExpressCompany("feikangda", "飞康达物流"));
        mExpressCompanyList.add(new ExpressCompany("fenghuangkuaidi", "凤凰快递"));
        mExpressCompanyList.add(new ExpressCompany("feikuaida", "飞快达"));
        mExpressCompanyList.add(new ExpressCompany("guotongkuaidi", "国通快递"));
        mExpressCompanyList.add(new ExpressCompany("ganzhongnengda", "港中能达物流"));
        mExpressCompanyList.add(new ExpressCompany("guangdongyouzhengwuliu", "广东邮政物流"));
        mExpressCompanyList.add(new ExpressCompany("gongsuda", "共速达"));
        mExpressCompanyList.add(new ExpressCompany("huitongkuaidi", "汇通快运"));
        mExpressCompanyList.add(new ExpressCompany("hengluwuliu", "恒路物流"));
        mExpressCompanyList.add(new ExpressCompany("huaxialongwuliu", "华夏龙物流"));
        mExpressCompanyList.add(new ExpressCompany("haihongwangsong", "海红"));
        mExpressCompanyList.add(new ExpressCompany("haiwaihuanqiu", "海外环球"));
        mExpressCompanyList.add(new ExpressCompany("jiayiwuliu", "佳怡物流"));
        mExpressCompanyList.add(new ExpressCompany("jinguangsudikuaijian", "京广速递"));
        mExpressCompanyList.add(new ExpressCompany("jixianda", "急先达"));
        mExpressCompanyList.add(new ExpressCompany("jjwl", "佳吉物流"));
        mExpressCompanyList.add(new ExpressCompany("jymwl", "加运美物流"));
        mExpressCompanyList.add(new ExpressCompany("jindawuliu", "金大物流"));
        mExpressCompanyList.add(new ExpressCompany("jialidatong", "嘉里大通"));
        mExpressCompanyList.add(new ExpressCompany("jykd", "晋越快递"));
        mExpressCompanyList.add(new ExpressCompany("kuaijiesudi", "快捷速递"));
        mExpressCompanyList.add(new ExpressCompany("lianb", "联邦快递（国内）"));
        mExpressCompanyList.add(new ExpressCompany("lianhaowuliu", "联昊通物流"));
        mExpressCompanyList.add(new ExpressCompany("longbanwuliu", "龙邦物流"));
        mExpressCompanyList.add(new ExpressCompany("lijisong", "立即送"));
        mExpressCompanyList.add(new ExpressCompany("lejiedi", "乐捷递"));
        mExpressCompanyList.add(new ExpressCompany("minghangkuaidi", "民航快递"));
        mExpressCompanyList.add(new ExpressCompany("meiguokuaidi", "美国快递"));
        mExpressCompanyList.add(new ExpressCompany("menduimen", "门对门"));
        mExpressCompanyList.add(new ExpressCompany("ocs", "OCS"));
        mExpressCompanyList.add(new ExpressCompany("peisihuoyunkuaidi", "配思货运"));
        mExpressCompanyList.add(new ExpressCompany("quanchenkuaidi", "全晨快递"));
        mExpressCompanyList.add(new ExpressCompany("quanfengkuaidi", "全峰快递"));
        mExpressCompanyList.add(new ExpressCompany("quanjitong", "全际通物流"));
        mExpressCompanyList.add(new ExpressCompany("quanritongkuaidi", "全日通快递"));
        mExpressCompanyList.add(new ExpressCompany("quanyikuaidi", "全一快递"));
        mExpressCompanyList.add(new ExpressCompany("rufengda", "如风达"));
        mExpressCompanyList.add(new ExpressCompany("santaisudi", "三态速递"));
        mExpressCompanyList.add(new ExpressCompany("shenghuiwuliu", "盛辉物流"));
        mExpressCompanyList.add(new ExpressCompany("shentong", "申通快递"));
        mExpressCompanyList.add(new ExpressCompany("shunfeng", "顺丰速运"));
        mExpressCompanyList.add(new ExpressCompany("sue", "速尔物流"));
        mExpressCompanyList.add(new ExpressCompany("shengfeng", "盛丰物流"));
        mExpressCompanyList.add(new ExpressCompany("saiaodi", "赛澳递"));
        mExpressCompanyList.add(new ExpressCompany("tiandihuayu", "天地华宇"));
        mExpressCompanyList.add(new ExpressCompany("tiantian", "天天快递"));
        mExpressCompanyList.add(new ExpressCompany("tnt", "tnt"));
        mExpressCompanyList.add(new ExpressCompany("ups", "ups"));
        mExpressCompanyList.add(new ExpressCompany("wanjiawuliu", "万家物流"));
        mExpressCompanyList.add(new ExpressCompany("wenjiesudi", "文捷航空速递"));
        mExpressCompanyList.add(new ExpressCompany("wuyuan", "伍圆"));
        mExpressCompanyList.add(new ExpressCompany("wxwl", "万象物流"));
        mExpressCompanyList.add(new ExpressCompany("xinbangwuliu", "新邦物流"));
        mExpressCompanyList.add(new ExpressCompany("xinfengwuliu", "信丰物流"));
        mExpressCompanyList.add(new ExpressCompany("yafengsudi", "亚风速递"));
        mExpressCompanyList.add(new ExpressCompany("yibangwuliu", "一邦速递"));
        mExpressCompanyList.add(new ExpressCompany("youshuwuliu", "优速物流"));
        mExpressCompanyList.add(new ExpressCompany("youzhengguonei", "邮政包裹"));
        mExpressCompanyList.add(new ExpressCompany("youzhengguoji", "邮政国际包裹"));
        mExpressCompanyList.add(new ExpressCompany("yuanchengwuliu", "远成物流"));
        mExpressCompanyList.add(new ExpressCompany("yuantong", "圆通速递"));
        mExpressCompanyList.add(new ExpressCompany("yuanweifeng", "源伟丰快递"));
        mExpressCompanyList.add(new ExpressCompany("yuanzhijiecheng", "元智捷诚快递"));
        mExpressCompanyList.add(new ExpressCompany("yunda", "韵达快运"));
        mExpressCompanyList.add(new ExpressCompany("yuntongkuaidi", "运通快递"));
        mExpressCompanyList.add(new ExpressCompany("yuefengwuliu", "越丰物流"));
        mExpressCompanyList.add(new ExpressCompany("yad", "源安达"));
        mExpressCompanyList.add(new ExpressCompany("yinjiesudi", "银捷速递"));
        mExpressCompanyList.add(new ExpressCompany("zhaijisong", "宅急送"));
        mExpressCompanyList.add(new ExpressCompany("zhongtiekuaiyun", "中铁快运"));
        mExpressCompanyList.add(new ExpressCompany("zhongtong", "中通速递"));
        mExpressCompanyList.add(new ExpressCompany("zhongyouwuliu", "中邮物流"));
        mExpressCompanyList.add(new ExpressCompany("zhongxinda", "忠信达"));
        mExpressCompanyList.add(new ExpressCompany("zhimakaimen", "芝麻开门"));
    }

    public String getCompanyName(String companyCode) {
        if (!TextUtils.isEmpty(companyCode)) {
            for (ExpressCompany company : mExpressCompanyList) {
                if (TextUtils.equals(companyCode, company.code)) {
                    return company.name;
                }
            }
        }
        return null;
    }
}

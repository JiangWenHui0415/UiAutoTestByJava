package com.purang.yyt_uiautotest.testdata.string;

import java.util.HashMap;
import java.util.Map;

public class CertificateCode {
	public static Map<String, Integer> areaCode = new HashMap<String, Integer>();

	/**
	 * data dictionary of area code of id cards.
	 **/
	static {
		areaCode.put("北京市", 110000);
		areaCode.put("天津市", 120000);
		areaCode.put("河北省", 130101);
		areaCode.put("石家庄市", 130101);
		areaCode.put("山西省", 140101);
		areaCode.put("太原市", 140101);
		areaCode.put("内蒙古省", 150101);
		areaCode.put("内蒙古自治区", 150101);
		areaCode.put("呼和浩特市", 150101);
		areaCode.put("辽宁省", 210101);
		areaCode.put("沈阳市", 210101);
		areaCode.put("吉林省", 220101);
		areaCode.put("长春市", 220101);
		areaCode.put("黑龙江省", 230101);
		areaCode.put("哈尔滨市", 230101);
		areaCode.put("上海市", 310100);
		areaCode.put("江苏省", 320101);
		areaCode.put("南京市", 320101);
		areaCode.put("浙江省", 330101);
		areaCode.put("杭州市", 330101);
		areaCode.put("安徽省", 340101);
		areaCode.put("合肥市", 340101);
		areaCode.put("福建省", 350101);
		areaCode.put("福州市", 350101);
		areaCode.put("江西省", 360101);
		areaCode.put("南昌市", 360101);
		areaCode.put("山东省", 370101);
		areaCode.put("济南市", 370101);
		areaCode.put("河南省", 410101);
		areaCode.put("郑州市", 410101);
		areaCode.put("湖北省", 420101);
		areaCode.put("武汉市", 420101);
		areaCode.put("湖南省", 430101);
		areaCode.put("长沙市", 430101);
		areaCode.put("广东省", 440101);
		areaCode.put("广州市", 440101);
		areaCode.put("广西省", 450101);
		areaCode.put("广西自治区", 450101);
		areaCode.put("广西壮族自治区", 450101);
		areaCode.put("南宁市", 450101);
		areaCode.put("重庆市", 500100);
		areaCode.put("四川省", 510101);
		areaCode.put("成都市", 510101);
		areaCode.put("贵州省", 520101);
		areaCode.put("贵阳市", 520101);
		areaCode.put("云南省", 530101);
		areaCode.put("昆明市", 530101);
		areaCode.put("西藏省", 540101);
		areaCode.put("西藏自治区", 540101);
		areaCode.put("西藏藏族自治区", 540101);
		areaCode.put("拉萨市", 540101);
		areaCode.put("陕西省", 610101);
		areaCode.put("西安市", 610101);
		areaCode.put("甘肃省", 620101);
		areaCode.put("兰州市", 620101);
		areaCode.put("青海省", 630101);
		areaCode.put("西宁市", 630101);
		areaCode.put("宁夏省", 640101);
		areaCode.put("宁夏自治区", 640101);
		areaCode.put("宁夏回族自治区", 640101);
		areaCode.put("银川市", 640101);
		areaCode.put("新疆省", 650101);
		areaCode.put("新疆维自治区", 650101);
		areaCode.put("新疆维吾尔族自治区", 650101);
		areaCode.put("乌鲁木齐市", 650101);
		areaCode.put("台湾省", 710000);
		areaCode.put("台北市", 710000);
		areaCode.put("香港", 810000);
		areaCode.put("香港特别行政区", 810000);
		areaCode.put("澳门", 820000);
		areaCode.put("澳门特别行政区", 820000);
	}

	/**
	 * generate random number for idno.
	 * 
	 * @param capCity
	 *            province name or capital city name of the province.
	 * @param birthDay
	 *            birth date of the person to get idnos.
	 * @param sexCode
	 *            sex of the person to get idnos.
	 **/
	public String getCertiCode(String capCity, String birthDay, String sexCode) {
		StringBuilder generater = new StringBuilder();
		generater.append(areaCode.get(capCity));
		generater.append(birthDay);
		generater.append(this.rndCodeWithSex(sexCode));
		generater.append(this.veriCodeCalc(generater.toString().toCharArray()));
		return generater.toString();
	}

	/**
	 * generate random number for idno.
	 * 
	 * @param cityCode
	 *            the city code of the chinese areas.
	 * @param birthDay
	 *            birth date of the person to get idnos.
	 * @param sexCode
	 *            sex of the person to get idnos.
	 **/
	public String getCertiCode(int cityCode, String birthDay, String sexCode) {
		StringBuilder generater = new StringBuilder();
		generater.append(cityCode);
		generater.append(birthDay);
		generater.append(this.rndCodeWithSex(sexCode));
		generater.append(this.veriCodeCalc(generater.toString().toCharArray()));
		return generater.toString();
	}

	/**
	 * generate verify no for idno.
	 **/
	public char veriCodeCalc(char[] chars) {
		if (chars.length < 17) {
			return ' ';
		}
		int[] c = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		char[] r = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
		int[] n = new int[17];
		int result = 0;
		for (int i = 0; i < n.length; i++) {
			n[i] = Integer.parseInt(chars[i] + "");
		}
		for (int i = 0; i < n.length; i++) {
			result += c[i] * n[i];
		}
		return r[result % 11];
	}

	/**
	 * generate sex number for idno.
	 **/
	public String rndCodeWithSex(String sexCode) {
		String rndString = "";
		int rndNum = (int) Math.rint((Math.random() * (998 - 1) + 1));
		if (rndNum % 2 == 1) {
			rndNum++;
		}
		if (("男").equals(sexCode) || ("M").equals(sexCode)) {
			rndString = strLeftExpand(String.valueOf(rndNum + 1), 3, '0');
		} else {
			rndString = strLeftExpand(String.valueOf(rndNum), 3, '0');
		}
		return rndString;
	}

	/**
	 * oracle lpad method in java.
	 * 
	 * @param appointedStr
	 *            original string
	 * @param finalLen
	 *            goal length of the string
	 * @param fillWith
	 *            chars to be filled with when string is shorter the goal
	 *            length.
	 **/
	public String strLeftExpand(String appointedStr, int finalLen, char fillWith) {
		String tempStr = appointedStr;
		while (tempStr.length() < finalLen) {
			tempStr = String.valueOf(fillWith) + tempStr;
		}
		return tempStr;
	}

	/**
	 * oracle rpad method in java.
	 * 
	 * @param appointedStr
	 *            original string
	 * @param finalLen
	 *            goal length of the string
	 * @param fillWith
	 *            chars to be filled with when string is shorter the goal
	 *            length.
	 **/
	public String strRightExpand(String appointedStr, int finalLen, char fillWith) {
		StringBuffer sb = new StringBuffer();
		while (sb.toString().length() < finalLen) {
			sb.append(fillWith);
		}
		return sb.toString();
	}
}

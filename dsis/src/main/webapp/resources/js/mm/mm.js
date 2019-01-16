/**
 * @description 通用函数
 * @version 1.0
 * @author mclin
 * @copyright Copyright Hand China Co.,Ltd.
 */

/**
* 快码下拉框渲染函数
* @param data
* @param value
*/
function getCodeDesc(data, value){
	/*console.log("getCodeDesc, data: " + JSON.stringify(data));
	console.log("getCodeDesc, value: " + value);*/
	for (var i in data) {
		if (data[i].value == value) {
			return data[i].meaning;
		}
	}
	return '';
}

/**
* 获取会员中英拼接名字
* @param data
* @param value
*/
function getConcatenateName(englishName, chineseName){
	var name = null;
	if (englishName != null) {
		name = englishName;
		if (chineseName != null) {
			name = name + "/" + chineseName;
		}
	} else if (chineseName != null) {
		name = chineseName;
	} 
	
	return name;
}

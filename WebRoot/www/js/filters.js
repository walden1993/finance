//创建自定义的Filters过滤器
/*
使用方式：
{{ expression | filter }}
内置的一些过滤器：
{{ "lower cap string" | uppercase }}
{{ {foo: "bar", baz: 23} | json }}
{{ 1304375948024 | date }}
{{ 1304375948024 | date:"MM/dd/yyyy @ h:mma" }}
*/
angular.module('starter.filters', [])
.filter('checkmark', function() {
  return function(input) {
    return input ? '\u2713' : '\u2718';//只能输入true跟false
  };
})
.filter('encrypt', function() {
  return function(input,len) {
	if(input){
		if(len && len>0){
			var x = "***"
			for(var i =0;i<len;i++){
				x = x+"*";
			}
			return input.replace(input.substr(0,len),x);
		}
	}
    return "未填写";
  };
})
.filter('ellipsis', function() {
  return function(input,len) {
	if(input){
		
	}
    return input;
  };
})
.filter('yyyyMMdd', function() {
	 return function(input) {
		return input.split(" ")[0];
	 };
});
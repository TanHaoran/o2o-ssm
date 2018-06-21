$(function() {
	var shopId = 1;
	var listUrl = '/o2o/shopAdmin/getProductCategoryList';
	var addUrl = '/o2o/shopAdmin/addProductCategorys';
	var deleteUrl = '/o2o/shopAdmin/removeProductCategory';

	getList();

	function getList() {
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								var dataList = data.data;
								$('.category-wrap').html('');
								var tempHtml = '';
								dataList
										.map(function(item, index) {
											tempHtml += ''
													+ '<div class="row row-product-category now">'
													+ '<div class="col-33 product-category-name">'
													+ item.productCategoryName
													+ '</div>'
													+ '<div class="col-33">'
													+ item.priority
													+ '</div>'
													+ '<div class="col-33"><a href="#" class="button delete" data-id="'
													+ item.productCategoryId
													+ '">删除</a></div>'
													+ '</div>';
										});
								$('.category-wrap').append(tempHtml);
							}
						});
	}
});
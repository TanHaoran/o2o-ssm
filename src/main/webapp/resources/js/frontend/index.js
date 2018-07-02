$(function() {
	var url = '/o2o/frontend/listMainPageInfo';

	$.getJSON(url, function(data) {
		if (data.success) {
			var headLineList = data.headLineList;
			var swiperHtml = '';
			// 遍历头条列表
			headLineList.map(function(item, index) {
				swiperHtml += '' + '<div class="swiper-slide img-wrap">'
						+ '<a href="' + item.lineImg
						+ '" external><img class="banner-img" src="'
						+ item.lineImg + '" alt="' + item.lineName + '"></a>'
						+ '</div>';
			});
			$('.swiper-wrapper').html(swiperHtml);
			// 设定轮播图轮换时间为3秒
			$(".swiper-container").swiper({
				autoplay : 3000,
				// 用户对轮播图操作时，是否自动停止
				autoplayDisableOnInteraction : false
			});
			// 获取大类列表
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml = '';
			// 拼接出两个一行的类别
			shopCategoryList.map(function(item, index) {
				categoryHtml += ''
						+ '<div class="col-50 shop-classify" data-category='
						+ item.shopCategoryId + '>' + '<div class="word">'
						+ '<p class="shop-title">' + item.shopCategoryName
						+ '</p>' + '<p class="shop-desc">'
						+ item.shopCategoryDesc + '</p>' + '</div>'
						+ '<div class="shop-classify-img-warp">'
						+ '<img class="shop-img" src="' + item.shopCategoryImg
						+ '">' + '</div>' + '</div>';
			});
			$('.row').html(categoryHtml);
		}
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});

	$('.row').on('click', '.shop-classify', function(e) {
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/o2o/frontend/shopList?parentId=' + shopCategoryId;
		window.location.href = newUrl;
	});

});

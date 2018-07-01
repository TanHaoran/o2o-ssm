$(function() {
	// 获取此店铺下的商品列表的URL
	var listUrl = '/o2o/shopAdmin/getProductListByShop?pageIndex=1&pageSize=999';
	// 商品下架URL
	var statusUrl = '/o2o/shopAdmin/modifyProduct';
	getList();

	function getList() {
		// 从后台获取此店铺的商品列表
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				productList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						// 若状态值是0，表明是已经下架的商品，操作变为上架（即点击上架按钮上架相关商品）
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-33">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="status" data-id="'
							+ item.productId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.product-wrap').html(tempHtml);
			}
		});
	}

	$('.product-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							// 如果有edit的class，则点击就进入店铺信息编辑页面，并带有productId参数
							window.location.href = '/o2o/shopAdmin/productOperation?productId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('status')) {
							// 如果有status的class，则调用上/下架相关商品，并带有productId参数
							changeItemStatus(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							// 如果有preview的class，则展示改商品详情页预览商品
							window.location.href = '/o2o/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});

	function changeItemStatus(id, enableStatus) {
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			// 上/下架相关商品
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}

	$('#new').click(function() {
		window.location.href = '/o2o/shopAdmin/productOperation';
	});
});
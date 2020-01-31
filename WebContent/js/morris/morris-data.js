/*
$(function() {


    Morris.Area({
	element: 'morris-area-chart',
    data: [
    <?php 
	
	$result = $dbo->getRowPerChart();
	
	$i = 0;
	
	while($row = pg_fetch_array($result)){ ?>  
	<?php if ($i>0) {echo ','; } ?>
	{
        period: '<?php echo $row['dt']; ?>',
        store: '<?php echo $row['st']; ?>',
        count: <?php echo $row['cn']; ?>
    }
    <?php $i++; } ?>],
    xkey: 'period',
    ykeys: ['store','count'],
    labels: ['store','count'],
    pointSize: 2,
    hideHover: 'auto',
    resize: true
    });

});
*/
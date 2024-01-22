
document.addEventListener("DOMContentLoaded", function() {
    // Sample data representing plots and roads
    const data = [
      { id: 1, name: 'Plot A', width: 80, height: 120, x: 100, y: 100 },
      { id: 2, name: 'Plot B', width: 100, height: 80, x: 300, y: 200 },
      { id: 3, name: 'Plot C', width: 120, height: 100, x: 500, y: 300 },
      {id: 4, name: 'Plot D', width:80, height: 10, x:120,y:120}
    ];
  
    // Calculate the maximum width and height to determine the SVG container size
    const maxPlotWidth = d3.max(data, d => d.width);
    const maxPlotHeight = d3.max(data, d => d.height);
  
    const svgWidth = maxPlotWidth + 200; // Add extra padding on both sides
    const svgHeight = maxPlotHeight + 200; // Add extra padding on both sides
  
    // Set up SVG container
    const svg = d3
      .select('body')
      .append('svg')
      .attr('width', svgWidth)
      .attr('height', svgHeight);
  
    // Create a group for plots
    const plotGroup = svg.append('g');
  
    // Create rectangles for plots
    const plots = plotGroup
      .selectAll('rect')
      .data(data)
      .enter()
      .append('rect')
      .attr('x', d => d.x - d.width / 2)
      .attr('y', d => d.y - d.height / 2)
      .attr('width', d => d.width)
      .attr('height', d => d.height)
      .style('fill', 'green'); // Set the initial fill color to green
  
    // Create a group for roads
    const roadGroup = svg.append('g');
  
    // Draw roads
    roadGroup
      .selectAll('line')
      .data(data)
      .enter()
      .append('line')
      .attr('x1', d => d.x)
      .attr('y1', d => d.y)
      .attr('x2', (d, i) => (i < data.length - 1 ? data[i + 1].x : data[0].x))
      .attr('y2', (d, i) => (i < data.length - 1 ? data[i + 1].y : data[0].y))
      .style('stroke', 'gray')
      .style('stroke-width', '2px');
  
    // Add labels to the plots
    plotGroup
      .selectAll('text')
      .data(data)
      .enter()
      .append('text')
      .attr('x', d => d.x)
      .attr('y', d => d.y)
      .text(d => d.name)
      .style('text-anchor', 'middle')
      .attr('dy', '0.35em')
      .style('font-size', '12px');
  });
  
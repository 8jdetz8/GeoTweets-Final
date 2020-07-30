
const btn = document.querySelector('#searchBtn');
const canvas = document.querySelector('#graph');
const status = document.querySelector('#status');

canvas.height = window.innerHeight - 200;

const ctx = canvas.getContext('2d');
let isLoading = false;
let chart;

async function getData(prog, sterm='Virginia'){
    if (prog > 3) return;
    let term = (sterm || 'Virginia').replace(/\//, '');
    if (/\#/.test(term)) term = term.replace('#','');
    return fetch(`/api/${prog}/search/${term}`).then(res => res.json());
}

function formatData(data){
    let labels = [];
    let dataset = [];

    data.forEach((ln)=>{
        if (ln.date) labels.push(`${ln.date} - ${ln.hour}`);
        else labels.push(ln.item);
        dataset.push(ln.val);
    });
    return {
        labels,
        dataset
    };
}

function clearCanvas(){
    if (!chart) return;
    chart.destroy();
    ctx.save();
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.restore();
    return true;
}



function loadGraph (data, program){ 
    let gtype = 'bar';
    let label = 'States Ranked By Tweet Count';

    if (program == '3') gtype = 'line', label =`Most Frequent Time of ${data.sterm}` ;
    if (program == '2') label = `Most Popular Hashtags in ${data.sterm}`; 

    clearCanvas();
    chart = new Chart(ctx, {
        type: gtype,
        data: {
            labels: data.labels,
            datasets: [{
                label: label,
                backgroundColor: 'rgb(0,180,204)',
                borderColor: 'rgb(0, 180, 204)',
                data: data.dataset
            }]
        },
        // Configuration options go here
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}

function searchAction(ev){
    if (isLoading) return;
    isLoading = true;
    ev.preventDefault();
    ev.stopPropagation();
    
    const program = document.querySelector('#program');
    const sterm = document.querySelector('#searchTerm');
    const pvalue = program.value;
    const svalue = sterm.value;
    
    if (chart) chart.destroy();
    
    if (!svalue && pvalue != 1) {
        status.innerText = 'Please input a search term';
        isLoading = false;
        return;
    }
    
    btn.disabled = true;
    status.innerText = 'Loading...';
    
    const dataPromise = getData(Number(pvalue), svalue);
    dataPromise.then(data => formatData(data)).then(data =>{
        status.innerText = '';
        btn.disabled = false;
        isLoading = false;
        if (data.dataset.length == 0) {
            if (pvalue == '2') status.innerHTML = `<i>"${svalue}"</i> is not a valid State, try again.`;
            if (pvalue == '3') status.innerHTML = `<i>"${svalue}"</i> not found, try again.`;
            return;
        }
        data.sterm = svalue;
        loadGraph(data, pvalue);
    });
}

btn.addEventListener('click', ev => searchAction(ev));

document.onkeydown = function (ev) {
    if (window.event.keyCode == '13') {
        searchAction(ev)
    }
}

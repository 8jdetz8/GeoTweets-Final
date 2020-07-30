
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
    if (program == '3') gtype = 'line';
    clearCanvas();
    
    chart = new Chart(ctx, {
        type: gtype,
        data: {
            labels: data.labels,
            datasets: [{
                label: `Results for "${data.sterm || 'Country'}"`,
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

btn.addEventListener('click', (ev)=>{
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
        data.sterm = svalue;
        loadGraph(data, pvalue);
    });
});
giornate = [];
giornate[35] = ["Parma-Napoli","Lazio-Inter","Milan-Roma"];
giornate[36] = ["Napoli-Cesena","Sampdoria-Lazio","Roma-Udinese"];
giornate[37] = ["Juve-Napoli","Lazio-Roma"];
giornate[38] = ["Napoli-Lazio","Roma-Palermo"];

classifica = [];
classifica["Juve"]=79; 
classifica["Roma"]=64; 
classifica["Lazio"]=63; 
classifica["Napoli"]=59;

possibilities3 = [
	['1','1','1'],	['x','1','1'], 	['2','1','1'],
	['1','1','x'], 	['x','1','x'], 	['2','1','x'],
	['1','1','2'],	['x','1','2'],	['2','1','2'],

	['1','x','1'], 	['x','x','1'], 	['2','x','1'],
	['1','x','x'], 	['x','x','x'], 	['2','x','x'],
	['1','x','2'], 	['x','x','2'], 	['2','x','2'],

	['1','2','1'], 	['x','2','1'], 	['2','2','1'],
	['1','2','x'], 	['x','2','x'], 	['2','2','x'],
	['1','2','2'], 	['x','2','2'], 	['2','2','2']
]

possibilities2 = [
	['1','1'], ['x','1'], ['2','1'],
	['1','2'], ['x','x'], ['2','x'],
	['1','x'], ['x','2'], ['2','2']
]

risultatiConseguiti = [];
//var risultatiConseguiti[35] = [
//    {
//        'match':"Parma-Napoli",
//        'result':1
//    },
//    {
//        'match':"Lazio-Inter",
//        'result':2
//    }
//];


function makeRisultati(day, risultatiConseguiti ,results){		
	
	for(var index in results){
		 elem = {
		    "match" :  results[index].match,
            "result" : results[index].result
        }
        if(risultatiConseguiti[day] == null){
            risultatiConseguiti[day] = [];
        }
        risultatiConseguiti[day].push(elem);
	}
	return risultatiConseguiti;
}

function calculatePoint(match, result){
	var teams = match.split("-");	
	switch(result){
		case '1' :
		    return  [{'team' : teams[0], 'point':3, 'match':match, 'result': result}, {'team' : teams[1], 'point':0, 'match':match, 'result': result}];

		case '2' :
		    return  [{'team' : teams[0], 'point':0, 'match':match, 'result': result}, {'team' : teams[1], 'point':3, 'match':match, 'result': result}];

		default :
		    return  [{'team' : teams[0], 'point':1, 'match':match, 'result': result}, {'team' : teams[1], 'point':1, 'match':match, 'result': result}];
	}
}

function updateClassifica(classificaCorrente, resultEntry1, resultEntry2){
	classifica[resultEntry1.team] += resultEntry1.point;
	classifica[resultEntry2.team] += resultEntry2.point;
	return classifica;
}

function copyArray(array){
	var newArray = [];
	for(var index in array){
		newArray[index] = array[index];
	}
	return newArray;
}

function makePrevisions(previsions, risultatiConseguiti, classicaTemporanea){
	var previsionNum = previsions.length;
	var prevision = { 'num': previsionNum, 'results':[], 'classifica': classicaTemporanea};
	for(var index in risultatiConseguiti){
		prevision.results.push(risultatiConseguiti[index]);
	}

	console.log("I make a prevision");	
	return prevision;
}

function goToChampions(classifica){	
	var romaPoints   = classifica["Roma"];
	var lazioPoints  = classifica["Lazio"];	
	var napoliPoints = classifica["Napoli"];

	if(napoliPoints < romaPoints && (romaPoints < lazioPoints || romaPoints == lazioPoints)){
		return false;		
	}	

	if(napoliPoints < lazioPoints && (lazioPoints < romaPoints || lazioPoints == romaPoints)){
		return false;
	}

	return true;		
}


function calculate(day, giornate, risultatiConseguiti, classifica, previsions){

	if(day > 38){
		if( goToChampions(classifica) ){
			return makePrevisions(previsions, risultatiConseguiti, classifica);
		}
        return null;
	}

	var partite = giornate[day];
	var classificaParziale = copyArray(classifica);	

	if(partite.length == 3){
		for(var index in possibilities3){
            var possibleResult = possibilities3[index];

			var result1 = calculatePoint(partite[0], possibleResult[0]);
			classificaParziale = updateClassifica(classificaParziale, result1[0], result1[1]);

			var result2 = calculatePoint(partite[1], possibleResult[1]);
			classificaParziale = updateClassifica(classificaParziale, result2[0], result2[1]);
			
			var result3 = calculatePoint(partite[2], possibleResult[2]);
			classificaParziale =updateClassifica(classificaParziale, result3[0], result3[1]);
						
			var previsione = calculate(day++, giornate, makeRisultati(day, risultatiConseguiti, [result1,result2,result3]), classifica);
			if(previsione != null){
			    previsions.push(previsione);
			}

			classificaParziale = copyArray(classifica);
		}
	}

	else{
		for(var index in possibilities2){
            var possibleResult = possibilities2[index];

			var result1 = calculatePoint(partite[0], possibleResult[0]);
            classificaParziale = updateClassifica(classificaParziale, result1[0], result1[1]);

            var result2 = calculatePoint(partite[1], possibleResult[1]);
            classificaParziale = updateClassifica(classificaParziale, result2[0], result2[1]);

            var previsione = calculate(day++, giornate, makeRisultati(day, risultatiConseguiti, [result1,result2,result3]), classifica);
            if(previsione != null){
                previsions.push(previsione);
            }

            classificaParziale = copyArray(classifica);
		}
	}

	return previsions;
}

var prevision = calculate(35, giornate, [], classifica, []);
console.log(prevision);


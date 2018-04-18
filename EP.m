nA = 2000;
nQ = 100;

logit = @(x) exp(x)./(1+exp(x));

GRUPOS = [1:60]';

for i=1:size(GRUPOS,1);
    cd dados
    mkdir(num2str(GRUPOS(i),'%02d'));
    cd(num2str(GRUPOS(i),'%02d'));
    
    thetaAluno = randn(1,nA);
    aQuestion = rand(1,nQ);
    bQuestion = rand(1,nQ);

    aux = [aQuestion' bQuestion'];
%    save questoes.txt -ASCII aux
    dlmwrite('questoes.txt',aux,' ');

    delta = [thetaAluno; -ones(1,nA)]'*[aQuestion; aQuestion.*bQuestion];
    y = rand(nA,nQ)<logit(delta);
    aux = int16(y');
%    save respostas.txt -ASCII aux
    dlmwrite('respostas.txt',aux,' ');

    cd ..
    
    cd ..
    
    cd origem
    save(num2str(GRUPOS(i),'%02d'),'aQuestion','bQuestion','thetaAluno');
    cd ..
end

return;

